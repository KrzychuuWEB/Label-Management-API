package pl.krzychuuweb.labelapp.batch;

import org.springframework.stereotype.Component;
import pl.krzychuuweb.labelapp.batch.dto.BatchCreateDTO;
import pl.krzychuuweb.labelapp.product.Product;

@Component
class BatchFactory {

    Batch createNewBatch(final BatchCreateDTO batchCreateDTO, final Product product) {
        return Batch.BatchBuilder.aBatch()
                .withSerial(batchCreateDTO.serial())
                .withExpirationDate(batchCreateDTO.expirationDate())
                .withIsShortDate(batchCreateDTO.isShortDate())
                .withCountry(batchCreateDTO.country())
                .withProduct(product)
                .build();
    }
}
