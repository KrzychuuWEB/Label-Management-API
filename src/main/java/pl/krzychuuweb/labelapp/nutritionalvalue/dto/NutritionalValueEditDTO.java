package pl.krzychuuweb.labelapp.nutritionalvalue.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NutritionalValueEditDTO(
        @NotBlank
        @Min(1)
        Long id,

        @NotBlank
        @Size(min = 1, max = 50)
        String name
) {
}
