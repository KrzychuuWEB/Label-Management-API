package pl.krzychuuweb.labelapp.nutritionalvalue.dto;

import jakarta.validation.constraints.NotNull;

public record EditNutritionalValueDTO(
        @NotNull
        Long id,

        @NotNull
        String name
) {
}
