package pl.krzychuuweb.labelapp.user;

import org.springframework.stereotype.Component;
import pl.krzychuuweb.labelapp.security.ownership.OwnershipStrategy;

@Component
class UserOwnershipStrategy implements OwnershipStrategy {

    private final UserQueryFacade userQueryFacade;

    UserOwnershipStrategy(final UserQueryFacade userQueryFacade) {
        this.userQueryFacade = userQueryFacade;
    }

    @Override
    public boolean isOwner(final Long userId, final Long resourceId) {
        return userQueryFacade.getUserById(resourceId).getId().equals(userId);
    }
}
