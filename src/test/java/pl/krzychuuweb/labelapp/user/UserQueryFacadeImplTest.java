package pl.krzychuuweb.labelapp.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.krzychuuweb.labelapp.exceptions.AlreadyExistsException;
import pl.krzychuuweb.labelapp.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserQueryFacadeImplTest {

    @Autowired
    private UserQueryRepository userQueryRepository;
    private UserQueryFacade userQueryFacade;

    @BeforeEach
    void setUp() {
        userQueryRepository = mock(UserQueryRepository.class);
        userQueryFacade = new UserQueryFacadeImpl(userQueryRepository);
    }

    @Test
    void should_get_all_users() {
        ArrayList<User> usersList = new ArrayList<>();
        usersList.add(User.UserBuilder.anUser().withFirstName("firstName1").withEmail("email1@email.com").withPassword("password1").build());
        usersList.add(User.UserBuilder.anUser().withFirstName("firstName2").withEmail("email2@email.com").withPassword("password2").build());

        when(userQueryRepository.findAll()).thenReturn(usersList);

        List<User> result = userQueryFacade.getAllUsers();

        assertThat(result).hasSize(usersList.size());
    }

    @Test
    void should_get_user_by_email() {
        User user = User.UserBuilder.anUser().withEmail("example@email.com").build();

        when(userQueryRepository.getByEmail(anyString())).thenReturn(Optional.of(user));

        User result = userQueryFacade.getUserByEmail(user.getEmail());

        assertThat(result).isEqualTo(user);
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void should_get_user_by_email_expect_exception() {
        when(userQueryRepository.getByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userQueryFacade.getUserByEmail(anyString()));
    }

    @Test
    void should_get_user_by_id() {
        User user = User.UserBuilder.anUser().withId(1L).build();

        when(userQueryRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User result = userQueryFacade.getUserById(user.getId());

        assertThat(result.getId()).isEqualTo(user.getId());
        assertThat(result).isEqualTo(user);
    }

    @Test
    void should_get_user_by_id_expect_exception() {
        when(userQueryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userQueryFacade.getUserById(anyLong()));
    }

    @Test
    void should_this_email_is_not_already_exists() {
        when(userQueryRepository.existsByEmail(anyString())).thenReturn(false);

        boolean result = userQueryFacade.checkIfEmailIsTaken(anyString());

        assertThat(result).isFalse();
    }

    @Test
    void should_this_email_already_exists() {
        when(userQueryRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(AlreadyExistsException.class, () -> userQueryFacade.checkIfEmailIsTaken(anyString()));
    }
}