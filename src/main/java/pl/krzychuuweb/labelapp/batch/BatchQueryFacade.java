package pl.krzychuuweb.labelapp.batch;

import pl.krzychuuweb.labelapp.product.Product;

import java.util.List;

public interface BatchQueryFacade {

    boolean checkWhetherSerialIsNotUsed(final String serial, final Product product);

    List<Batch> getAllBatchesByProduct(final Long productId);

    Batch getById(final Long id);
}
