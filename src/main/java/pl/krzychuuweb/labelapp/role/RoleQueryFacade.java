package pl.krzychuuweb.labelapp.role;

import pl.krzychuuweb.labelapp.user.User;

import java.util.List;

public interface RoleQueryFacade {

    Role getRoleByUserRole(UserRole userRole);

    List<Role> getRolesForUser(User user);
}
