package pl.krzychuuweb.labelapp.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        usersList.add(User.UserBuilder.anUser().but().withUsername("Username1").withEmail("email1@email.com").withPassword("password1").build());
        usersList.add(User.UserBuilder.anUser().but().withUsername("Username2").withEmail("email2@email.com").withPassword("password2").build());

        when(userQueryRepository.findAll()).thenReturn(usersList);

        List<User> result = userQueryFacade.getAll();

        assertThat(result).hasSize(usersList.size());
    }

    @Test
    void should_get_user_by_email() {
        User user = User.UserBuilder.anUser().but().withEmail("example@email.com").build();

        when(userQueryRepository.getByEmail(anyString())).thenReturn(Optional.of(user));

        User result = userQueryFacade.getUserByEmail(user.getEmail());

        assertThat(result).isEqualTo(user);
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void should_get_user_by_email_expect_exception() {
        when(userQueryRepository.getByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userQueryFacade.getUserByEmail(anyString()));
    }
}