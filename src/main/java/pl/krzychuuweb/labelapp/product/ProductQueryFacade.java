package pl.krzychuuweb.labelapp.product;

import pl.krzychuuweb.labelapp.QueryFacade;

import java.util.List;

public interface ProductQueryFacade extends QueryFacade {

    boolean checkWhetherSlugIsNotUsedByUser(final String slug);

    Product getById(final Long id);

    Product getProductBySlugAndAuthUser(final String slug);

    List<Product> getAllProductByAuthUser();
}
