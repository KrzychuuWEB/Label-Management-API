package pl.krzychuuweb.labelapp.initial.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record InitialCreateDTO(
        @NotBlank
        @Size(min = 1, max = 50)
        String firstName,

        @NotBlank
        @Size(min = 1, max = 100)
        String lastName,

        @NotBlank
        @Size(min = 1, max = 15)
        String name
) {
}
