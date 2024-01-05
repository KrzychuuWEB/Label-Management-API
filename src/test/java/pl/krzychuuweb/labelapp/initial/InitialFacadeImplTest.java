package pl.krzychuuweb.labelapp.initial;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.krzychuuweb.labelapp.auth.AuthQueryFacade;
import pl.krzychuuweb.labelapp.exception.BadRequestException;
import pl.krzychuuweb.labelapp.initial.dto.InitialCreateDTO;
import pl.krzychuuweb.labelapp.initial.dto.InitialEditDTO;
import pl.krzychuuweb.labelapp.user.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class InitialFacadeImplTest {

    @Autowired
    private InitialRepository initialRepository;

    @Autowired
    private InitialQueryFacade initialQueryFacade;

    @Autowired
    private AuthQueryFacade authQueryFacade;

    @Autowired
    private InitialFactory initialFactory;

    private InitialFacade initialFacade;

    @BeforeEach
    void setup() {
        initialRepository = mock(InitialRepository.class);
        initialQueryFacade = mock(InitialQueryFacade.class);
        authQueryFacade = mock(AuthQueryFacade.class);
        initialFactory = mock(InitialFactory.class);
        initialFacade = new InitialFacadeImpl(initialRepository, initialQueryFacade, authQueryFacade, initialFactory);
    }

    @Test
    void should_add_new_initial_but_initial_name_is_used() {
        InitialCreateDTO initialCreateDTO = new InitialCreateDTO("firstName", "lastName", "FL");

        when(initialQueryFacade.checkWhetherInitialNameIsNotUsed(anyString())).thenReturn(false);

        assertThatThrownBy(() -> initialFacade.add(initialCreateDTO)).isInstanceOf(BadRequestException.class);
    }

    @Test
    void should_add_new_initial() {
        InitialCreateDTO initialCreateDTO = new InitialCreateDTO("firstName", "lastName", "FL");
        User user = User.UserBuilder.anUser().withEmail("email@email.com").build();
        Initial initial = Initial.InitialBuilder.anInitial().withUser(user).withName("FL").build();

        when(initialQueryFacade.checkWhetherInitialNameIsNotUsed(anyString())).thenReturn(true);
        when(authQueryFacade.getLoggedUser()).thenReturn(user);
        when(initialFactory.createInitialWithLoggedUser(initialCreateDTO, user)).thenReturn(initial);
        when(initialRepository.save(any(Initial.class))).thenReturn(initial);

        Initial result = initialFacade.add(initialCreateDTO);

        assertThat(result.getName()).isEqualTo(initialCreateDTO.name());
        assertThat(result.getUser().getEmail()).isEqualTo("email@email.com");
    }

    @Test
    void should_edit_initial_but_name_is_used() {
        InitialEditDTO initialEditDTO = new InitialEditDTO(1L, "firstName", "lastName", "FL");

        when(initialQueryFacade.checkWhetherInitialNameIsNotUsed(anyString())).thenReturn(false);

        assertThatThrownBy(() -> initialFacade.edit(initialEditDTO)).isInstanceOf(BadRequestException.class);
    }

    @Test
    void should_edit_initial() {
        InitialEditDTO initialEditDTO = new InitialEditDTO(1L, "newFirstName", "newLastName", "newName");
        User user = User.UserBuilder.anUser().withEmail("email@email.com").build();
        Initial initial = Initial.InitialBuilder.anInitial().withUser(user).withName("oldName").withLastName("oldLastName").withFirstName("oldFirstName").build();

        when(initialQueryFacade.checkWhetherInitialNameIsNotUsed(anyString())).thenReturn(true);
        when(authQueryFacade.getLoggedUser()).thenReturn(user);
        when(initialQueryFacade.getById(anyLong())).thenReturn(initial);
        when(initialRepository.save(any(Initial.class))).thenReturn(initial);

        Initial result = initialFacade.edit(initialEditDTO);

        assertThat(result.getFirstName()).isEqualTo(initialEditDTO.firstName());
        assertThat(result.getLastName()).isEqualTo(initialEditDTO.lastName());
        assertThat(result.getName()).isEqualTo(initialEditDTO.name());
        assertThat(result.getUser()).isInstanceOf(User.class);
    }

    @Test
    void should_delete_initial() {
        Initial initial = Initial.InitialBuilder.anInitial().build();

        when(initialQueryFacade.getById(anyLong())).thenReturn(initial);

        initialFacade.deleteById(anyLong());

        verify(initialRepository, times(1)).delete(any());
    }
}