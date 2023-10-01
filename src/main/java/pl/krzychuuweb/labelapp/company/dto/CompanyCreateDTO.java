package pl.krzychuuweb.labelapp.company.dto;

import jakarta.validation.constraints.Size;

public record CompanyCreateDTO(
        @Size(min = 1)
        String name,
        @Size(min = 1)
        String footer
) {
}
