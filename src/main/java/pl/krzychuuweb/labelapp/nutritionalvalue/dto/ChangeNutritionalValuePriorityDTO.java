package pl.krzychuuweb.labelapp.nutritionalvalue.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ChangeNutritionalValuePriorityDTO(
        @NotBlank
        Long id,

        @NotBlank
        @Min(1)
        Integer priority
) {
}
