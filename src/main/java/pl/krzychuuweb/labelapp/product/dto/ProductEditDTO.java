package pl.krzychuuweb.labelapp.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductEditDTO(
        @NotNull
        Long id,

        @NotBlank
        @Size(max = 70)
        String name,

        @Size(max = 250)
        String description,

        @Size(max = 100)
        String composition
) {
}
