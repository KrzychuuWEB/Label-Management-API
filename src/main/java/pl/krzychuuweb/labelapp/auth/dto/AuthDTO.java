package pl.krzychuuweb.labelapp.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthDTO(
        @NotBlank
        String token
) {
}
