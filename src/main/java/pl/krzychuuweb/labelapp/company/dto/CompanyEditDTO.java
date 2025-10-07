package pl.krzychuuweb.labelapp.company.dto;

import jakarta.validation.constraints.NotBlank;

public record CompanyEditDTO(
        @NotBlank
        Long id,

        @NotBlank
        String name,

        @NotBlank
        String footer
) {
}
