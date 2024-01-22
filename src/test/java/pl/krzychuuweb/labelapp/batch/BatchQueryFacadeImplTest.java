package pl.krzychuuweb.labelapp.batch;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.krzychuuweb.labelapp.auth.AuthQueryFacade;
import pl.krzychuuweb.labelapp.user.User;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@ExtendWith(MockitoExtension.class)
class BatchQueryFacadeImplTest {

    @Mock
    private BatchQueryRepository batchQueryRepository;

    @Mock
    private AuthQueryFacade authQueryFacade;

    @InjectMocks
    private BatchQueryFacadeImpl batchQueryFacade;

    @Test
    void should_check_serial_if_serial_is_not_used_by_user() {
        User user = User.UserBuilder.anUser().withEmail("email@email.com").build();

        when(authQueryFacade.getLoggedUser()).thenReturn(user);
        when(batchQueryRepository.existsBySerialAndUser(anyString(), any(User.class))).thenReturn(false);

        boolean result = batchQueryFacade.checkWhetherSerialIsNotUsed("serial");

        assertTrue(result);
    }

    @Test
    void should_check_serial_if_serial_is_used_by_user() {
        User user = User.UserBuilder.anUser().withEmail("email@email.com").build();

        when(authQueryFacade.getLoggedUser()).thenReturn(user);
        when(batchQueryRepository.existsBySerialAndUser(anyString(), any(User.class))).thenReturn(true);

        boolean result = batchQueryFacade.checkWhetherSerialIsNotUsed("serial");

        assertFalse(result);
    }
}