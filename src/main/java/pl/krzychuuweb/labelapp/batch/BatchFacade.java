package pl.krzychuuweb.labelapp.batch;

import pl.krzychuuweb.labelapp.batch.dto.BatchCreateDTO;
import pl.krzychuuweb.labelapp.batch.dto.BatchEditDTO;

public interface BatchFacade {

    Batch create(final BatchCreateDTO batchCreateDTO, final Long productId);

    Batch edit(final BatchEditDTO batchEditDTO, final Long productId);
}
