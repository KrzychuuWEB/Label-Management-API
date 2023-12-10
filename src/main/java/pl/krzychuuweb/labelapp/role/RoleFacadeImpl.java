package pl.krzychuuweb.labelapp.role;

import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.user.User;
import pl.krzychuuweb.labelapp.user.UserQueryFacade;

import java.util.List;

@Service
class RoleFacadeImpl implements RoleFacade {

    private final RoleRepository roleRepository;

    private final UserQueryFacade userQueryFacade;

    private final RoleQueryFacade roleQueryFacade;

    RoleFacadeImpl(final RoleRepository roleRepository, final UserQueryFacade userQueryFacade, final RoleQueryFacade roleQueryFacade) {
        this.roleRepository = roleRepository;
        this.userQueryFacade = userQueryFacade;
        this.roleQueryFacade = roleQueryFacade;
    }

    @Override
    public List<Role> assignRole(final Long userId, final UserRole userRole) {
        User user = userQueryFacade.getUserById(userId);
        Role role = roleQueryFacade.getRoleByUserRole(userRole);

        role.getUsers().add(user);
        roleRepository.save(role);

        return roleQueryFacade.getRolesForUser(user);
    }
}
