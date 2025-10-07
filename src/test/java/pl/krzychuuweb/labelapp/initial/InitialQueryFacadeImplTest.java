package pl.krzychuuweb.labelapp.initial;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.krzychuuweb.labelapp.auth.AuthQueryFacade;
import pl.krzychuuweb.labelapp.exception.NotFoundException;
import pl.krzychuuweb.labelapp.user.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InitialQueryFacadeImplTest {

    @Autowired
    private InitialQueryRepository initialQueryRepository;

    @Autowired
    private AuthQueryFacade authQueryFacade;

    private InitialQueryFacade initialQueryFacade;

    @BeforeEach
    void setup() {
        initialQueryRepository = mock(InitialQueryRepository.class);
        authQueryFacade = mock(AuthQueryFacade.class);
        initialQueryFacade = new InitialQueryFacadeImpl(initialQueryRepository, authQueryFacade);
    }

    @Test
    void should_get_initial_by_id() {
        User user = User.UserBuilder.anUser()
                .withEmail("email@email.com")
                .build();
        Initial initial = Initial.InitialBuilder.anInitial()
                .withFirstName("firstName")
                .withLastName("lastName")
                .withName("FL")
                .withUser(user)
                .build();

        when(initialQueryRepository.findById(anyLong())).thenReturn(Optional.of(initial));

        Initial result = initialQueryFacade.getById(anyLong());

        assertThat(result.getFirstName()).isEqualTo(initial.getFirstName());
        assertThat(result.getLastName()).isEqualTo(initial.getLastName());
        assertThat(result.getName()).isEqualTo(initial.getName());
        assertThat(result.getUser()).isInstanceOf(User.class);
        assertThat(result.getUser().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void should_initial_is_not_found_test_get_by_id() {
        when(initialQueryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> initialQueryFacade.getById(anyLong())).isInstanceOf(NotFoundException.class);
    }

    @Test
    void should_get_all_initials() {
        User user = User.UserBuilder.anUser().withEmail("email@email.com").build();
        List<Initial> initials = List.of(
                Initial.InitialBuilder.anInitial().withUser(user).build(),
                Initial.InitialBuilder.anInitial().withUser(user).build(),
                Initial.InitialBuilder.anInitial().withUser(user).build()
        );

        when(authQueryFacade.getLoggedUser()).thenReturn(user);
        when(initialQueryRepository.findAllByUser(any(User.class))).thenReturn(initials);

        List<Initial> result = initialQueryFacade.getAllByLoggedUser();

        assertThat(result).hasSameSizeAs(initials);
    }

    @Test
    void should_check_whether_user_used_initial_name() {
        User user = User.UserBuilder.anUser().withEmail("email@email.com").build();

        when(authQueryFacade.getLoggedUser()).thenReturn(user);
        when(initialQueryRepository.existsByNameAndUser(anyString(), any(User.class))).thenReturn(true);

        boolean result = initialQueryFacade.checkWhetherInitialNameIsNotUsed("test");

        assertThat(result).isFalse();
    }

    @Test
    void should_check_whether_user_not_used_initial_name() {
        User user = User.UserBuilder.anUser().withEmail("email@email.com").build();

        when(authQueryFacade.getLoggedUser()).thenReturn(user);
        when(initialQueryRepository.existsByNameAndUser(anyString(), any(User.class))).thenReturn(false);

        boolean result = initialQueryFacade.checkWhetherInitialNameIsNotUsed("test");

        assertThat(result).isTrue();
    }
}