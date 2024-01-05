package pl.krzychuuweb.labelapp.initial.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record InitialEditDTO(
        @NotNull
        @Min(1)
        Long id,

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
