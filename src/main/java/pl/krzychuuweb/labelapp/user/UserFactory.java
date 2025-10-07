package pl.krzychuuweb.labelapp.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.krzychuuweb.labelapp.user.dto.UserCreateDTO;

@Component
class UserFactory {

    private final PasswordEncoder passwordEncoder;

    UserFactory(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    User createUser(UserCreateDTO userCreateDTO) {
        return User.UserBuilder.anUser()
                .withFirstName(userCreateDTO.firstName())
                .withEmail(userCreateDTO.email())
                .withPassword(passwordEncoder.encode(userCreateDTO.password()))
                .build();
    }
}
