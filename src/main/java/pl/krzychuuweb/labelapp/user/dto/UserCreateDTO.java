package pl.krzychuuweb.labelapp.user.dto;

import jakarta.validation.constraints.Size;

public record UserCreateDTO(
        @Size(min = 1, max = 80)
        String firstName,
        @Size(min = 1, max = 255)
        String email,
        @Size(min = 8)
        String password
) {
}
