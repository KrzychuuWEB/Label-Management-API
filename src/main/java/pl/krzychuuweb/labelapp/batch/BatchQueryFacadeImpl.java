package pl.krzychuuweb.labelapp.batch;

import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.auth.AuthQueryFacade;
import pl.krzychuuweb.labelapp.user.User;

@Service
class BatchQueryFacadeImpl implements BatchQueryFacade {

    private final BatchQueryRepository batchQueryRepository;

    private final AuthQueryFacade authQueryFacade;

    BatchQueryFacadeImpl(BatchQueryRepository batchQueryRepository, AuthQueryFacade authQueryFacade) {
        this.batchQueryRepository = batchQueryRepository;
        this.authQueryFacade = authQueryFacade;
    }

    @Override
    public boolean checkWhetherSerialIsNotUsed(final String serial) {
        User user = authQueryFacade.getLoggedUser();

        return !batchQueryRepository.existsBySerialAndUser(serial, user);
    }
}
