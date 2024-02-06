package pl.krzychuuweb.labelapp.batch;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.krzychuuweb.labelapp.batch.dto.BatchCreateDTO;
import pl.krzychuuweb.labelapp.batch.dto.BatchDTO;
import pl.krzychuuweb.labelapp.batch.dto.BatchEditDTO;
import pl.krzychuuweb.labelapp.exception.BadRequestException;
import pl.krzychuuweb.labelapp.product.ProductOwnershipStrategy;
import pl.krzychuuweb.labelapp.security.ownership.CheckOwnership;

import java.util.List;

@RestController
@RequestMapping("/products")
class BatchController {

    private final BatchFacade batchFacade;

    private final BatchQueryFacade batchQueryFacade;

    BatchController(BatchFacade batchFacade, BatchQueryFacade batchQueryFacade) {
        this.batchFacade = batchFacade;
        this.batchQueryFacade = batchQueryFacade;
    }

    @PostMapping("/{id}/batches")
    @ResponseStatus(HttpStatus.CREATED)
    @CheckOwnership(ProductOwnershipStrategy.class)
    BatchDTO add(@Valid @RequestBody BatchCreateDTO batchCreateDTO, @PathVariable Long id) {
        return BatchDTO.mapToBatchDTO(batchFacade.create(batchCreateDTO, id));
    }

    @GetMapping("/{productId}/batches/{batchId}")
    @CheckOwnership(ProductOwnershipStrategy.class)
    BatchDTO getByIdAndProduct(@PathVariable Long productId, @PathVariable Long batchId) {
        return BatchDTO.mapToBatchDTO(batchQueryFacade.getById(batchId));
    }

    @GetMapping("/{productId}/batches")
    @CheckOwnership(ProductOwnershipStrategy.class)
    List<BatchDTO> getAllByProduct(@PathVariable Long productId) {
        return BatchDTO.mapToBatchDTOList(batchQueryFacade.getAllBatchesByProduct(productId));
    }

    @PutMapping("/{productId}/batches/{batchId}")
    @CheckOwnership(ProductOwnershipStrategy.class)
    BatchDTO edit(@Valid @RequestBody BatchEditDTO batchEditDTO, @PathVariable Long productId, @PathVariable Long batchId) {
        if (!batchId.equals(batchEditDTO.id())) {
            throw new BadRequestException("Id is not same");
        }

        return BatchDTO.mapToBatchDTO(batchFacade.edit(batchEditDTO, productId));
    }
}
