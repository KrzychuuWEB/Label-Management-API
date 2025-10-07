package pl.krzychuuweb.labelapp.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.krzychuuweb.labelapp.user.dto.UserCreateDTO;
import pl.krzychuuweb.labelapp.user.dto.UserEditDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class UserFacadeImplTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserQueryFacade userQueryFacade;
    @Autowired
    private UserFactory userFactory;
    private UserFacade userFacade;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userQueryFacade = mock(UserQueryFacade.class);
        userFactory = mock(UserFactory.class);
        userFacade = new UserFacadeImpl(userQueryFacade, userRepository, userFactory);
    }

    @Test
    void should_create_user() {
        UserCreateDTO userCreateDTO = new UserCreateDTO("firstName", "any@email.com", "secret");
        User user = User.UserBuilder.anUser().withFirstName(userCreateDTO.firstName()).withEmail(userCreateDTO.email()).withPassword(userCreateDTO.password()).build();

        when(userRepository.save(any())).thenReturn(user);

        User result = userFacade.addUser(userCreateDTO);

        assertThat(result.getFirstName()).isEqualTo(userCreateDTO.firstName());
        assertThat(result.getEmail()).isEqualTo(userCreateDTO.email());
        assertThat(result.getPassword()).isEqualTo(userCreateDTO.password());
    }

    @Test
    void should_update_user() {
        UserEditDTO userEditDTO = new UserEditDTO(1L, "newFirstName", "new@email.com");
        User userWithEditFields = User.UserBuilder.anUser().withId(userEditDTO.id()).withFirstName(userEditDTO.firstName()).withEmail(userEditDTO.email()).build();
        User user = User.UserBuilder.anUser().withId(1L).withFirstName("oldFirstName").withEmail("old@email.com").build();

        when(userQueryFacade.getUserById(anyLong())).thenReturn(user);
        when(userRepository.save(user)).thenReturn(userWithEditFields);

        User result = userFacade.updateUser(userEditDTO);

        assertThat(result.getId()).isEqualTo(userEditDTO.id());
        assertThat(result.getFirstName()).isEqualTo(userEditDTO.firstName());
        assertThat(result.getEmail()).isEqualTo(userEditDTO.email());
    }

    @Test
    void should_delete_user() {
        User user = User.UserBuilder.anUser().build();
        when(userQueryFacade.getUserById(anyLong())).thenReturn(user);

        userFacade.deleteUserById(anyLong());

        verify(userRepository, times(1)).deleteById(anyLong());
    }
}