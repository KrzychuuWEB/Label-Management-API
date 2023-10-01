package pl.krzychuuweb.labelapp.company.dto;

import jakarta.validation.constraints.NotNull;

public record CompanyEditDTO(
        @NotNull
        Long id,
        @NotNull
        String name,
        @NotNull
        String footer
) {
}
