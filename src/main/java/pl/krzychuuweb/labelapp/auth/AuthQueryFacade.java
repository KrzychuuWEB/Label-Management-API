package pl.krzychuuweb.labelapp.auth;

import org.springframework.security.access.AccessDeniedException;
import pl.krzychuuweb.labelapp.user.User;

public interface AuthQueryFacade {

    String getLoggedUserEmail();

    boolean whetherUserHasAssignmentForResource(User user) throws AccessDeniedException;

    User getLoggedUser();
}
