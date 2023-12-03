package pl.krzychuuweb.labelapp.auth;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.user.User;

@Service
class AuthQueryFacadeImpl implements AuthQueryFacade {

    @Override
    public String getLoggedUserEmail() {
        return getAuthentication().getName();
    }

    @Override
    public boolean whetherUserHasAssignmentForResource(final User user) throws AccessDeniedException {
        if (!user.getEmail().equals(getLoggedUserEmail())) {
            throw new AccessDeniedException("You don't have access for this resource");
        }

        return true;
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
