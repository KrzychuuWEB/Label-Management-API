package pl.krzychuuweb.labelapp.initial;

import org.springframework.stereotype.Component;
import pl.krzychuuweb.labelapp.initial.dto.InitialCreateDTO;
import pl.krzychuuweb.labelapp.user.User;

@Component
class InitialFactory {

    Initial createInitialWithLoggedUser(final InitialCreateDTO initialCreateDTO, final User user) {
        return Initial.InitialBuilder.anInitial()
                .withName(initialCreateDTO.name())
                .withFirstName(initialCreateDTO.firstName())
                .withLastName(initialCreateDTO.lastName())
                .withUser(user)
                .build();
    }
}
