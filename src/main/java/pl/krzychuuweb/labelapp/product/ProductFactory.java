package pl.krzychuuweb.labelapp.product;

import org.springframework.stereotype.Component;
import pl.krzychuuweb.labelapp.product.dto.ProductCreateDTO;
import pl.krzychuuweb.labelapp.user.User;

@Component
class ProductFactory {

    private final ProductService productService;

    ProductFactory(ProductService productService) {
        this.productService = productService;
    }

    Product createProduct(final ProductCreateDTO productCreateDTO, final User user) {
        return Product.ProductBuilder.aProduct()
                .withName(productCreateDTO.name())
                .withDescription(productCreateDTO.description())
                .withComposition(productCreateDTO.composition())
                .withSlug(productService.generateSlug(productCreateDTO.name()))
                .withUser(user)
                .build();
    }
}
