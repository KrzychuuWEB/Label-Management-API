package pl.krzychuuweb.labelapp.user;

import java.util.List;

interface UserQueryFacade {

    User getUserByEmail(String email);

    List<User> getAll();
}
