package pl.krzychuuweb.labelapp.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.krzychuuweb.labelapp.exceptions.AlreadyExistsException;
import pl.krzychuuweb.labelapp.exceptions.NotFoundException;
import pl.krzychuuweb.labelapp.role.Role;
import pl.krzychuuweb.labelapp.role.UserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

        assertThatThrownBy(() -> {
            userQueryFacade.getUserByEmail(anyString());
        }).isInstanceOf(NotFoundException.class);
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

        assertThatThrownBy(() -> userQueryFacade.getUserById(anyLong())).isInstanceOf(NotFoundException.class);
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

        assertThatThrownBy(() -> {
            userQueryFacade.checkIfEmailIsTaken(anyString());
        }).isInstanceOf(AlreadyExistsException.class);
    }

    @Test
    void should_check_user_role() {
        Role role = Role.RoleBuilder.aRole().withName(UserRole.ROLE_USER).build();
        User user = User.UserBuilder.anUser().withId(1L).withRoles(Set.of(role)).build();

        when(userQueryRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User result = userQueryFacade.getUserById(user.getId());

        assertThat(result.getId()).isEqualTo(user.getId());
        assertThat(result).isEqualTo(user);
        assertThat(result.getRoles().stream().findFirst().get().getName()).isEqualTo(role.getName());
    }
}