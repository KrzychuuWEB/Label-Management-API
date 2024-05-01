package pl.krzychuuweb.labelapp.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductEditDTO(
        @NotBlank
        Long id,

        @NotBlank
        @Size(max = 70)
        String name,

        @NotBlank
        @Size(max = 250)
        String description,

        @NotBlank
        @Size(max = 100)
        String composition
) {
}
