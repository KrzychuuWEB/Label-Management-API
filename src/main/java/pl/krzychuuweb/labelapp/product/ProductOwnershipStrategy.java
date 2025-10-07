package pl.krzychuuweb.labelapp.product;

import org.springframework.stereotype.Component;
import pl.krzychuuweb.labelapp.security.ownership.AbstractOwnershipStrategy;

@Component
public class ProductOwnershipStrategy extends AbstractOwnershipStrategy<Product> {

    private final ProductQueryRepository productQueryRepository;

    ProductOwnershipStrategy(ProductQueryRepository productQueryRepository) {
        super(productQueryRepository);
        this.productQueryRepository = productQueryRepository;
    }
}
