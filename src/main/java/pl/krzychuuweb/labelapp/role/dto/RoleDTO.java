package pl.krzychuuweb.labelapp.role.dto;

import pl.krzychuuweb.labelapp.role.Role;
import pl.krzychuuweb.labelapp.role.UserRole;
import pl.krzychuuweb.labelapp.user.dto.UserDTO;

import java.util.List;
import java.util.Objects;

public record RoleDTO(
        Long id,

        UserRole name,
        UserDTO user) {

    public static List<RoleDTO> mapRoleListToRoleDTOList(List<Role> roles) {
        return roles.stream()
                .map(role -> new RoleDTO(
                                role.getId(),
                                role.getName(),
                                UserDTO.mapUserToUserDTO(Objects.requireNonNull(role.getUsers().stream().findFirst().orElse(null)))
                        )
                ).toList();
    }
}
