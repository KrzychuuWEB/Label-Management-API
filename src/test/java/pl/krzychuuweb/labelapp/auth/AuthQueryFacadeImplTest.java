package pl.krzychuuweb.labelapp.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.krzychuuweb.labelapp.user.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthQueryFacadeImplTest {

    private AuthQueryFacade authQueryFacade;

    @BeforeEach
    void setUp() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("email@email.com");

        authQueryFacade = new AuthQueryFacadeImpl();
    }

    @Test
    void should_get_user_email() {
        String result = authQueryFacade.getLoggedUserEmail();

        assertThat(result).isEqualTo("email@email.com");
    }

    @Test
    void should_check_whether_user_has_access_to_resources() {
        User user = User.UserBuilder.anUser().withEmail("email@email.com").build();
        when(authQueryFacade.getLoggedUserEmail()).thenReturn("email@email.com");

        assertDoesNotThrow(() -> authQueryFacade.whetherUserHasAssignmentForResource(user));
    }

    @Test
    void should_whether_user_not_has_access_to_resources() {
        User user = User.UserBuilder.anUser().withEmail("bad@email.com").build();
        when(authQueryFacade.getLoggedUserEmail()).thenReturn("email@email.com");

        assertThrows(AccessDeniedException.class, () -> authQueryFacade.whetherUserHasAssignmentForResource(user));
    }
}