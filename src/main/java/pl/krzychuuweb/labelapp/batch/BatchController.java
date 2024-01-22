package pl.krzychuuweb.labelapp.batch;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.krzychuuweb.labelapp.batch.dto.BatchCreateDTO;
import pl.krzychuuweb.labelapp.batch.dto.BatchDTO;

@RestController
class BatchController {

    private final BatchFacade batchFacade;

    BatchController(BatchFacade batchFacade) {
        this.batchFacade = batchFacade;
    }

    @PostMapping("/products/{id}/batches")
    @ResponseStatus(HttpStatus.CREATED)
    BatchDTO add(@Valid @RequestBody BatchCreateDTO batchCreateDTO, @PathVariable Long id) {
        return BatchDTO.mapToBatchDTO(batchFacade.create(batchCreateDTO));
    }
}
