package pl.krzychuuweb.labelapp.initial;

import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.auth.AuthQueryFacade;
import pl.krzychuuweb.labelapp.exception.NotFoundException;
import pl.krzychuuweb.labelapp.user.User;

import java.util.List;

@Service
class InitialQueryFacadeImpl implements InitialQueryFacade {

    private final InitialQueryRepository initialQueryRepository;

    private final AuthQueryFacade authQueryFacade;

    InitialQueryFacadeImpl(InitialQueryRepository initialQueryRepository, AuthQueryFacade authQueryFacade) {
        this.initialQueryRepository = initialQueryRepository;
        this.authQueryFacade = authQueryFacade;
    }

    @Override
    public Initial getById(final Long id) {
        return initialQueryRepository.findById(id).orElseThrow(() -> new NotFoundException("Initial with this id is not exists"));
    }

    @Override
    public List<Initial> getAllByLoggedUser() {
        User user = authQueryFacade.getLoggedUser();

        return initialQueryRepository.findAllByUser(user);
    }

    @Override
    public boolean checkWhetherInitialNameIsNotUsed(final String name) {
        User user = authQueryFacade.getLoggedUser();

        return !initialQueryRepository.existsByNameAndUser(name, user);
    }
}
