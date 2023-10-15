package pl.krzychuuweb.labelapp.user;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.exceptions.AlreadyExistsException;
import pl.krzychuuweb.labelapp.exceptions.NotFoundException;
import pl.krzychuuweb.labelapp.user.dto.UserCreateDTO;
import pl.krzychuuweb.labelapp.user.dto.UserEditDTO;

@Service
class UserFacadeImpl implements UserFacade {

    private final UserQueryFacade userQueryFacade;

    private final UserRepository userRepository;

    private final UserFactory userFactory;

    UserFacadeImpl(final UserQueryFacade userQueryFacade, final UserRepository userRepository, final UserFactory userFactory) {
        this.userQueryFacade = userQueryFacade;
        this.userRepository = userRepository;
        this.userFactory = userFactory;
    }

    @Override
    @Transactional
    public User addUser(final UserCreateDTO userCreateDTO) throws AlreadyExistsException {
        userQueryFacade.checkIfEmailIsTaken(userCreateDTO.email());

        return userRepository.save(userFactory.createUser(userCreateDTO));
    }

    @Override
    @Transactional
    public User updateUser(final UserEditDTO userEditDTO) throws AlreadyExistsException {
        userQueryFacade.checkIfEmailIsTaken(userEditDTO.email());

        User user = userQueryFacade.getUserById(userEditDTO.id());
        user.setFirstName(userEditDTO.firstName());
        user.setEmail(userEditDTO.email());

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUserById(final Long id) throws NotFoundException {
        User user = userQueryFacade.getUserById(id);

        if (user != null) {
            userRepository.deleteById(id);
        }
    }
}
