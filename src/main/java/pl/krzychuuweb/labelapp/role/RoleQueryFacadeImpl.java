package pl.krzychuuweb.labelapp.role;

import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.exception.NotFoundException;
import pl.krzychuuweb.labelapp.user.User;

import java.util.List;

@Service
class RoleQueryFacadeImpl implements RoleQueryFacade {

    private final RoleQueryRepository roleQueryRepository;

    RoleQueryFacadeImpl(final RoleQueryRepository roleQueryRepository) {
        this.roleQueryRepository = roleQueryRepository;
    }

    @Override
    public Role getRoleByUserRole(final UserRole userRole) {
        return roleQueryRepository.getByName(userRole).orElseThrow(() -> new NotFoundException("Role not found!"));
    }

    @Override
    public List<Role> getRolesForUser(final User user) {
        return roleQueryRepository.getRolesByUserId(user.getId());
    }
}
