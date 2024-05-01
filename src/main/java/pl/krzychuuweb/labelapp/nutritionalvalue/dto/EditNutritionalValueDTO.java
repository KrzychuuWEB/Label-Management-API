package pl.krzychuuweb.labelapp.nutritionalvalue.dto;

import jakarta.validation.constraints.NotBlank;

public record EditNutritionalValueDTO(
        @NotBlank
        Long id,

        @NotBlank
        String name
) {
}
