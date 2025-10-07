package pl.krzychuuweb.labelapp.batch;

import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.batch.dto.BatchCreateDTO;
import pl.krzychuuweb.labelapp.batch.dto.BatchEditDTO;
import pl.krzychuuweb.labelapp.exception.BadRequestException;
import pl.krzychuuweb.labelapp.product.Product;
import pl.krzychuuweb.labelapp.product.ProductQueryFacade;

@Service
class BatchFacadeImpl implements BatchFacade {

    private final BatchRepository batchRepository;

    private final BatchQueryFacade batchQueryFacade;

    private final BatchFactory batchFactory;

    private final ProductQueryFacade productQueryFacade;

    BatchFacadeImpl(BatchRepository batchRepository, BatchQueryFacade batchQueryFacade, BatchFactory batchFactory, ProductQueryFacade productQueryFacade) {
        this.batchRepository = batchRepository;
        this.batchQueryFacade = batchQueryFacade;
        this.batchFactory = batchFactory;
        this.productQueryFacade = productQueryFacade;
    }

    @Override
    public Batch create(final BatchCreateDTO batchCreateDTO, final Long productId) {
        Product product = productQueryFacade.getById(productId);

        if (!batchQueryFacade.checkWhetherSerialIsNotUsed(batchCreateDTO.serial(), product)) {
            throw new BadRequestException("Such a serial number already exists for this product");
        }

        return batchRepository.save(
                batchFactory.createNewBatch(batchCreateDTO, product)
        );
    }

    @Override
    public Batch edit(final BatchEditDTO batchEditDTO, final Long productId) {
        Batch batch = batchQueryFacade.getById(batchEditDTO.id());
        Product product = productQueryFacade.getById(productId);

        if (!batchQueryFacade.checkWhetherSerialIsNotUsed(batchEditDTO.serial(), product) && !batch.getSerial().equals(batchEditDTO.serial())) {
            throw new BadRequestException("This serial in the this product is used!");
        }

        batch.setSerial(batchEditDTO.serial());
        batch.setExpirationDate(batchEditDTO.expirationDate());
        batch.setShortDate(batchEditDTO.isShortDate());
        batch.setCountry(batchEditDTO.country());

        return batchRepository.save(batch);
    }
}
