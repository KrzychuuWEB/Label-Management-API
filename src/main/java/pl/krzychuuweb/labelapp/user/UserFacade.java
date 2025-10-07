package pl.krzychuuweb.labelapp.user;

import pl.krzychuuweb.labelapp.user.dto.UserCreateDTO;
import pl.krzychuuweb.labelapp.user.dto.UserEditDTO;

public interface UserFacade {

    User addUser(final UserCreateDTO userCreateDTO);

    User updateUser(final UserEditDTO userEditDTO);

    void deleteUserById(final Long id);
}
