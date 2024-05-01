package pl.krzychuuweb.labelapp.batchnutritionalmapping;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.krzychuuweb.labelapp.batchnutritionalmapping.dto.BatchNutritionalMappingCreateDTO;
import pl.krzychuuweb.labelapp.product.ProductOwnershipStrategy;
import pl.krzychuuweb.labelapp.security.ownership.CheckOwnership;

@RestController
@RequestMapping("/products")
class BatchNutritionalMappingController {

    @PostMapping("{productId}/batches/{batchId}/nutritional-values")
    @ResponseStatus(HttpStatus.CREATED)
    @CheckOwnership(ProductOwnershipStrategy.class)
    BatchNutritionalMapping add(
            @Valid @RequestBody BatchNutritionalMappingCreateDTO createDTO,
            @PathVariable Long productId,
            @PathVariable Long batchId
    ) {
        return null;
    }
}
