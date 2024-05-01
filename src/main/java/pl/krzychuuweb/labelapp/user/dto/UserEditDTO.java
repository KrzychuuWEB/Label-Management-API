package pl.krzychuuweb.labelapp.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserEditDTO(
        @NotBlank
        Long id,
        @NotBlank
        @Size(min = 1, max = 80)
        String firstName,
        @NotBlank
        @Size(min = 1, max = 255)
        String email

) {
}
