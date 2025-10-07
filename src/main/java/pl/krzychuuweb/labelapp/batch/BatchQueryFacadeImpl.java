package pl.krzychuuweb.labelapp.batch;

import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.exception.NotFoundException;
import pl.krzychuuweb.labelapp.product.Product;
import pl.krzychuuweb.labelapp.product.ProductQueryFacade;

import java.util.List;

@Service
class BatchQueryFacadeImpl implements BatchQueryFacade {

    private final BatchQueryRepository batchQueryRepository;

    private final ProductQueryFacade productQueryFacade;

    BatchQueryFacadeImpl(BatchQueryRepository batchQueryRepository, ProductQueryFacade productQueryFacade) {
        this.batchQueryRepository = batchQueryRepository;
        this.productQueryFacade = productQueryFacade;
    }

    @Override
    public boolean checkWhetherSerialIsNotUsed(final String serial, final Product product) {
        return !batchQueryRepository.existsBySerialAndProduct(serial, product);
    }

    @Override
    public List<Batch> getAllBatchesByProduct(final Long productId) {
        Product product = productQueryFacade.getById(productId);

        return batchQueryRepository.getAllByProduct(product);
    }

    @Override
    public Batch getById(final Long id) throws NotFoundException {
        return batchQueryRepository.findById(id).orElseThrow(() -> new NotFoundException("Batch with this id is not found"));
    }
}
