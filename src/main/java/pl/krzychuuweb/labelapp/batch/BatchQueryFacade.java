package pl.krzychuuweb.labelapp.batch;

import pl.krzychuuweb.labelapp.product.Product;

public interface BatchQueryFacade {

    boolean checkWhetherSerialIsNotUsed(final String serial, final Product product);
}
