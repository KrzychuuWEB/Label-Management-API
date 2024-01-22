package pl.krzychuuweb.labelapp.batch;

import org.springframework.stereotype.Component;
import pl.krzychuuweb.labelapp.batch.dto.BatchCreateDTO;

@Component
class BatchFactory {

    Batch createNewBatch(final BatchCreateDTO batchCreateDTO) {
        return Batch.BatchBuilder.aBatch()
                .withSerial(batchCreateDTO.serial())
                .withExpirationDate(batchCreateDTO.expirationDate())
                .withIsShortDate(batchCreateDTO.isShortDate())
                .withCountry(batchCreateDTO.country())
                .build();
    }
}
