package pl.krzychuuweb.labelapp;

import pl.krzychuuweb.labelapp.auth.AuthQueryFacade;
import pl.krzychuuweb.labelapp.user.User;

public abstract class AbstractQueryFacade<T extends BaseEntity> {

    private final BaseQueryRepository<T> baseQueryRepository;

    private final AuthQueryFacade authQueryFacade;

    protected AbstractQueryFacade(BaseQueryRepository<T> baseQueryRepository, AuthQueryFacade authQueryFacade) {
        this.baseQueryRepository = baseQueryRepository;
        this.authQueryFacade = authQueryFacade;
    }

    public boolean checkWhetherNameInResourceIsNotUsedByLoggedUser(final String name) {
        User user = authQueryFacade.getLoggedUser();

        return !baseQueryRepository.existsByNameAndUser(name, user);
    }
}
