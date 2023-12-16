package pl.krzychuuweb.labelapp.nutritionalvalue.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record CreateNutritionalValueDTO(
        @Size(min = 1, max = 80)
        String name,
        @Min(1)
        Float priority
) {
}