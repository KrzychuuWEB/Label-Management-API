package pl.krzychuuweb.labelapp.user;

import java.util.List;

public interface UserQueryFacade {

    User getUserById(final Long id);

    User getUserByEmail(final String email);

    List<User> getAllUsers();

    boolean checkIfEmailIsTaken(final String email);
}
