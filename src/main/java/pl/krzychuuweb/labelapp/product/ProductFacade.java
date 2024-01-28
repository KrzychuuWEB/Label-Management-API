package pl.krzychuuweb.labelapp.product;

import pl.krzychuuweb.labelapp.product.dto.ProductCreateDTO;
import pl.krzychuuweb.labelapp.product.dto.ProductEditDTO;

public interface ProductFacade {

    Product create(final ProductCreateDTO productCreateDTO);

    Product edit(final ProductEditDTO productEditDTO);

    void deleteById(final Long id);
}
