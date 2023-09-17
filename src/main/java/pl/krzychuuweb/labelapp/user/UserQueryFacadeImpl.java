package pl.krzychuuweb.labelapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class UserQueryFacadeImpl implements UserQueryFacade {

    @Autowired
    private final UserQueryRepository userQueryRepository;

    UserQueryFacadeImpl(final UserQueryRepository userQueryRepository) {
        this.userQueryRepository = userQueryRepository;
    }

    @Override
    public User getUserByEmail(String email) {
        return userQueryRepository.getByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Override
    public List<User> getAll() {
        return userQueryRepository.findAll();
    }
}
