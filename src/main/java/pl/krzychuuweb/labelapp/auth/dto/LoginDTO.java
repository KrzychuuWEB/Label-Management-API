package pl.krzychuuweb.labelapp.auth.dto;

import jakarta.validation.constraints.NotNull;

public record LoginDTO(@NotNull String email, @NotNull String password) {
}
