package pl.krzychuuweb.labelapp.role;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.krzychuuweb.labelapp.role.dto.AssignRoleDTO;
import pl.krzychuuweb.labelapp.role.dto.RoleDTO;

import java.util.List;

@RestController
@RequestMapping("/roles")
@Secured("ROLE_ADMIN")
class RoleController {

    private final RoleFacade roleFacade;

    RoleController(final RoleFacade roleFacade) {
        this.roleFacade = roleFacade;
    }

    @PostMapping
    List<RoleDTO> assignRoleForUser(@RequestBody AssignRoleDTO assignRoleDTO) {
        UserRole userRole = UserRole.changeStringToUserRole(assignRoleDTO.roleName());
        return RoleDTO.mapRoleListToRoleDTOList(roleFacade.assignRole(assignRoleDTO.userId(), userRole));
    }
}
