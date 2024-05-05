package pl.krzychuuweb.labelapp.batchnutritionalmapping;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.krzychuuweb.labelapp.batchnutritionalmapping.dto.BatchNutritionalMappingCreateDTO;
import pl.krzychuuweb.labelapp.product.ProductOwnershipStrategy;
import pl.krzychuuweb.labelapp.security.ownership.CheckOwnership;

import java.util.List;

@RestController
@RequestMapping("/products")
class BatchNutritionalMappingController {

    private final BatchNutritionalMappingFacade batchNutritionalMappingFacade;

    BatchNutritionalMappingController(final BatchNutritionalMappingFacade batchNutritionalMappingFacade) {
        this.batchNutritionalMappingFacade = batchNutritionalMappingFacade;
    }

    @PostMapping("{productId}/batches/{batchId}/nutritionals")
    @ResponseStatus(HttpStatus.CREATED)
    @CheckOwnership(ProductOwnershipStrategy.class)
    List<BatchNutritionalMapping> add(
            @Valid @RequestBody BatchNutritionalMappingCreateDTO createDTO,
            @PathVariable Long productId,
            @PathVariable Long batchId
    ) {
        return batchNutritionalMappingFacade.add(createDTO, batchId);
    }
}
