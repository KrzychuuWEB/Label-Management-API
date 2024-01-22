package pl.krzychuuweb.labelapp.batch;

import pl.krzychuuweb.labelapp.batch.dto.BatchCreateDTO;

public interface BatchFacade {

    Batch create(final BatchCreateDTO batchCreateDTO);
}
