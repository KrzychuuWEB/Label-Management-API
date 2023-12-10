package pl.krzychuuweb.labelapp.company;

import org.springframework.stereotype.Component;
import pl.krzychuuweb.labelapp.security.ownership.OwnershipStrategy;

@Component
class CompanyOwnershipStrategy implements OwnershipStrategy {

    private final CompanyQueryFacade companyQueryFacade;

    CompanyOwnershipStrategy(final CompanyQueryFacade companyQueryFacade) {
        this.companyQueryFacade = companyQueryFacade;
    }

    @Override
    public boolean isOwner(final Long userId, final Long companyId) {
        return userId.equals(companyQueryFacade.getById(companyId).getUser().getId());
    }
}
