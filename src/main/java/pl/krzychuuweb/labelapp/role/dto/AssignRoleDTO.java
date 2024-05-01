package pl.krzychuuweb.labelapp.role.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AssignRoleDTO(
        @Size(min = 1, max = 80)
        String roleName,

        @NotBlank
        Long userId
) {
}
