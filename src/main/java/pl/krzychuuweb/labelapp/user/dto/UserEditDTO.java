package pl.krzychuuweb.labelapp.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserEditDTO(
        @NotNull
        Long id,
        @NotNull
        @Size(min = 1, max = 80)
        String username,
        @NotNull
        @Size(min = 1, max = 255)
        String email

) {
}
