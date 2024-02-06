package pl.krzychuuweb.labelapp.batch.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record BatchCreateDTO(

        @NotBlank
        @NotNull
        @Size(max = 40)
        String serial,

        @NotNull
        @Future(message = "Expiration date must be in the future")
        LocalDate expirationDate,

        @NotNull
        boolean isShortDate,

        @NotNull
        @NotBlank
        @Size(max = 40)
        String country
) {
}
