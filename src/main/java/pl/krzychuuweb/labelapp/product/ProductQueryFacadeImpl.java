package pl.krzychuuweb.labelapp.product;

import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.AbstractQueryFacade;
import pl.krzychuuweb.labelapp.auth.AuthQueryFacade;
import pl.krzychuuweb.labelapp.exception.NotFoundException;
import pl.krzychuuweb.labelapp.user.User;

import java.util.List;

@Service
class ProductQueryFacadeImpl extends AbstractQueryFacade<Product> implements ProductQueryFacade {

    private final ProductQueryRepository productQueryRepository;

    private final AuthQueryFacade authQueryFacade;

    protected ProductQueryFacadeImpl(ProductQueryRepository productQueryRepository, AuthQueryFacade authQueryFacade) {
        super(productQueryRepository, authQueryFacade);
        this.productQueryRepository = productQueryRepository;
        this.authQueryFacade = authQueryFacade;
    }

    @Override
    public boolean checkWhetherSlugIsNotUsedByUser(final String slug) {
        return !productQueryRepository.existsBySlugAndUser(slug, getAuthUser());
    }

    @Override
    public Product getById(final Long id) {
        return productQueryRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found product with this id"));
    }

    @Override
    public Product getProductBySlugAndAuthUser(final String slug) {
        return productQueryRepository.getBySlugAndUser(slug, getAuthUser()).orElseThrow(() -> new NotFoundException("Product with this slug is not found"));
    }

    @Override
    public List<Product> getAllProductByAuthUser() {
        return productQueryRepository.getAllByUser(getAuthUser());
    }

    private User getAuthUser() {
        return authQueryFacade.getLoggedUser();
    }
}
