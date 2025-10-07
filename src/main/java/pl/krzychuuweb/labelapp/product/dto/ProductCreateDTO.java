package pl.krzychuuweb.labelapp.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductCreateDTO(
        @NotBlank
        @Size(max = 70)
        String name,

        @Size(max = 250)
        String description,

        @Size(max = 100)
        String composition
) {
}
