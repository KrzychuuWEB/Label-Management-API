package pl.krzychuuweb.labelapp.user;

import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.exceptions.AlreadyExistsException;
import pl.krzychuuweb.labelapp.exceptions.NotFoundException;

import java.util.List;

@Service
class UserQueryFacadeImpl implements UserQueryFacade {

    private final UserQueryRepository userQueryRepository;

    public UserQueryFacadeImpl(final UserQueryRepository userQueryRepository) {
        this.userQueryRepository = userQueryRepository;
    }

    @Override
    public User getUserById(final Long id) throws NotFoundException {
        return userQueryRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public User getUserByEmail(final String email) throws NotFoundException {
        return userQueryRepository.getByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public List<User> getAllUsers() {
        return userQueryRepository.findAll();
    }

    @Override
    public boolean checkIfUsernameIsTaken(final String username) throws AlreadyExistsException {
        if (userQueryRepository.existsByUsername(username)) {
            throw new AlreadyExistsException("This username already exists");
        }

        return false;
    }

    @Override
    public boolean checkIfEmailIsTaken(final String email) throws AlreadyExistsException {
        if (userQueryRepository.existsByEmail(email)) {
            throw new AlreadyExistsException("This email already exists");
        }

        return false;
    }
}
