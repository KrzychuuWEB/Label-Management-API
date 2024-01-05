package pl.krzychuuweb.labelapp.initial.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record InitialCreateDTO(
        @NotNull
        @Size(min = 1, max = 50)
        String firstName,

        @NotNull
        @Size(min = 1, max = 100)
        String lastName,

        @NotNull
        @Size(min = 1, max = 15)
        String name
) {
}
