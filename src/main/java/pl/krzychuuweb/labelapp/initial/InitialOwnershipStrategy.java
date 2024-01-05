package pl.krzychuuweb.labelapp.initial;

import org.springframework.stereotype.Component;
import pl.krzychuuweb.labelapp.security.ownership.OwnershipStrategy;

@Component
class InitialOwnershipStrategy implements OwnershipStrategy {

    private final InitialQueryFacade initialQueryFacade;

    InitialOwnershipStrategy(final InitialQueryFacade initialQueryFacade) {
        this.initialQueryFacade = initialQueryFacade;
    }

    @Override
    public boolean isOwner(final Long userId, final Long resourceId) {
        return initialQueryFacade.getById(resourceId).getUser().getId().equals(userId);
    }
}
