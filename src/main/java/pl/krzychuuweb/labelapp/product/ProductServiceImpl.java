package pl.krzychuuweb.labelapp.product;

import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
class ProductServiceImpl implements ProductService {

    private final ProductQueryFacade productQueryFacade;

    ProductServiceImpl(ProductQueryFacade productQueryFacade) {
        this.productQueryFacade = productQueryFacade;
    }

    @Override
    public String generateSlug(final String name) throws IllegalArgumentException {
        String slug = normalizeString(name);

        checkStringWhetherNotHasSpecialCharacters(slug);

        return ifSlugIsUedGenerateRandomNumbersForEndSlug(slug);
    }

    private String normalizeString(final String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase()
                .replace(" ", "-")
                .replace("ł", "l");
    }

    private void checkStringWhetherNotHasSpecialCharacters(final String string) throws IllegalArgumentException {
        Pattern pattern = Pattern.compile("[^a-z0-9-]");
        Matcher matcher = pattern.matcher(string);

        if (matcher.find()) {
            throw new IllegalArgumentException("This name have special characters, please delete special characters");
        }
    }

    private String ifSlugIsUedGenerateRandomNumbersForEndSlug(final String slug) {
        Random random = new Random();

        if (!productQueryFacade.checkWhetherSlugIsNotUsedByUser(slug)) {
            return generateSlug(slug + random.nextInt(10000));
        }

        return slug;
    }
}
