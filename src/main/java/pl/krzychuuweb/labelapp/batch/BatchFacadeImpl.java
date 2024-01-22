package pl.krzychuuweb.labelapp.batch;

import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.batch.dto.BatchCreateDTO;
import pl.krzychuuweb.labelapp.exception.BadRequestException;

@Service
class BatchFacadeImpl implements BatchFacade {

    private final BatchRepository batchRepository;

    private final BatchQueryFacade batchQueryFacade;

    private final BatchFactory batchFactory;

    BatchFacadeImpl(BatchRepository batchRepository, BatchQueryFacade batchQueryFacade, BatchFactory batchFactory) {
        this.batchRepository = batchRepository;
        this.batchQueryFacade = batchQueryFacade;
        this.batchFactory = batchFactory;
    }

    @Override
    public Batch create(final BatchCreateDTO batchCreateDTO) {
        if (!batchQueryFacade.checkWhetherSerialIsNotUsed(batchCreateDTO.serial())) {
            throw new BadRequestException("Such a serial number already exists");
        }

        return batchRepository.save(
                batchFactory.createNewBatch(batchCreateDTO)
        );
    }
}
