package pl.krzychuuweb.labelapp.batch;

import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.product.Product;

@Service
class BatchQueryFacadeImpl implements BatchQueryFacade {

    private final BatchQueryRepository batchQueryRepository;

    BatchQueryFacadeImpl(BatchQueryRepository batchQueryRepository) {
        this.batchQueryRepository = batchQueryRepository;
    }

    @Override
    public boolean checkWhetherSerialIsNotUsed(final String serial, final Product product) {
        return !batchQueryRepository.existsBySerialAndProduct(serial, product);
    }
}
