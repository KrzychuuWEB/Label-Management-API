package pl.krzychuuweb.labelapp.role;

import java.util.List;

public interface RoleFacade {

    List<Role> assignRole(final Long userId, UserRole userRole);
}
