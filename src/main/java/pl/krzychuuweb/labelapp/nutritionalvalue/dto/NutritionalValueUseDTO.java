package pl.krzychuuweb.labelapp.nutritionalvalue.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record NutritionalValueUseDTO(

        @NotBlank
        @Min(1)
        Long id,

        @NotBlank
        String value
) {
}
