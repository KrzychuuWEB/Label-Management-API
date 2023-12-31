package pl.krzychuuweb.labelapp.nutritionalvalue.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ChangeNutritionalValuePriorityDTO(
        @NotNull
        Long id,
        @NotNull
        @Min(1)
        Integer priority
) {
}
