package pl.krzychuuweb.labelapp.user;

import pl.krzychuuweb.labelapp.user.dto.UserCreateDTO;

class UserFactory {

    UserFactory() {
    }

    static User createUser(UserCreateDTO userCreateDTO) {
        return User.UserBuilder.anUser()
                .withUsername(userCreateDTO.username())
                .withEmail(userCreateDTO.email())
                .withPassword(userCreateDTO.password())
                .build();
    }
}
