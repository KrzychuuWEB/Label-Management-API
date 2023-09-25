package pl.krzychuuweb.labelapp.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.krzychuuweb.labelapp.IntegrationTestConfig;
import pl.krzychuuweb.labelapp.exceptions.AlreadyExistsException;
import pl.krzychuuweb.labelapp.exceptions.BadRequestException;
import pl.krzychuuweb.labelapp.exceptions.NotFoundException;
import pl.krzychuuweb.labelapp.user.dto.UserCreateDTO;
import pl.krzychuuweb.labelapp.user.dto.UserDTO;
import pl.krzychuuweb.labelapp.user.dto.UserEditDTO;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerIT extends IntegrationTestConfig {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserQueryFacade userQueryFacade;

    @Test
    @Transactional
    void should_get_all_users() throws Exception {
        List<User> users = List.of(
                User.UserBuilder.anUser().but().withUsername("username1").withPassword("username1").withEmail("usernam1@email.com").build(),
                User.UserBuilder.anUser().but().withUsername("username2").withPassword("username2").withEmail("usernam2@email.com").build()
        );
        userRepository.saveAll(users);

        MvcResult result = mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andReturn();

        List<UserDTO> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertThat(response).hasSize(users.size());
    }

    @Test
    @Transactional
    void should_get_user_by_id() throws Exception {
        User user = User.UserBuilder.anUser().build();
        userRepository.save(user);

        MvcResult result = mockMvc.perform(get("/users/" + user.getId()))
                .andExpect(status().isOk())
                .andReturn();

        UserDTO response = objectMapper.readValue(result.getResponse().getContentAsString(), UserDTO.class);

        assertThat(response.id()).isEqualTo(user.getId());
    }

    @Test
    @Transactional
    void should_create_user() throws Exception {
        UserCreateDTO userCreateDTO = new UserCreateDTO("username", "new@email.com", "secret1234");

        MvcResult result2 = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateDTO))
                )
                .andExpect(status().isCreated())
                .andReturn();
        UserDTO response = objectMapper.readValue(result2.getResponse().getContentAsString(), UserDTO.class);

        assertThat(response.username()).isEqualTo(userCreateDTO.username());
        assertThat(response.email()).isEqualTo(userCreateDTO.email());
        assertThat(response.createdAt()).isBefore(LocalDateTime.now());
    }
    @Test
    @Transactional
    void should_create_user_throw_exception_when_username_already_exists() throws Exception {
        UserCreateDTO userCreateDTO = new UserCreateDTO("takenUsername", "new@email.com", "secret123456");
        userRepository.save(User.UserBuilder.anUser().withUsername(userCreateDTO.username()).build());

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateDTO))
                )
                .andExpect(status().isConflict())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(AlreadyExistsException.class));
    }

    @Test
    @Transactional
    void should_create_user_throw_exception_when_email_already_exists() throws Exception {
        UserCreateDTO userCreateDTO = new UserCreateDTO("username", "taken@email.com", "secret123456");
        userRepository.save(User.UserBuilder.anUser().withEmail(userCreateDTO.email()).build());

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateDTO))
                )
                .andExpect(status().isConflict())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(AlreadyExistsException.class));
    }

    @Test
    @Transactional
    void should_update_user_by_id() throws Exception {
        User savedUser = userRepository.save(
                User.UserBuilder.anUser().withUsername("oldUsername").withEmail("old@email.com").build()
        );
        UserEditDTO userEditDTO = new UserEditDTO(savedUser.getId(), "newUsername", "new@email.com");

        MvcResult result = mockMvc.perform(put("/users/" + savedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userEditDTO))
                )
                .andExpect(status().isOk())
                .andReturn();

        UserDTO response = objectMapper.readValue(result.getResponse().getContentAsString(), UserDTO.class);

        assertThat(response.username()).isEqualTo(userEditDTO.username());
        assertThat(response.email()).isEqualTo(userEditDTO.email());
    }

    @Test
    @Transactional
    void should_update_user_throw_exception_when_username_already_exists() throws Exception {
        UserEditDTO userEditDTO = new UserEditDTO(1L, "takenUsername", "email@email.com");
        userRepository.save(User.UserBuilder.anUser().withUsername(userEditDTO.username()).build());

        mockMvc.perform(put("/users/" + userEditDTO.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userEditDTO))
                )
                .andExpect(status().isConflict())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(AlreadyExistsException.class));
    }

    @Test
    @Transactional
    void should_update_user_throw_exception_when_email_already_exists() throws Exception {
        UserEditDTO userEditDTO = new UserEditDTO(1L,"username", "taken@email.com");
        userRepository.save(User.UserBuilder.anUser().withEmail(userEditDTO.email()).build());

        mockMvc.perform(put("/users/" + userEditDTO.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userEditDTO))
                )
                .andExpect(status().isConflict())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(AlreadyExistsException.class));
    }

    @Test
    void should_update_user_by_id_with_id_is_not_same() throws Exception {
        UserEditDTO userEditDTO = new UserEditDTO(1L, "newUsername", "new@email.com");

        mockMvc.perform(put("/users/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userEditDTO))
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(BadRequestException.class));
    }

    @Test
    @Transactional
    void should_delete_user_by_id() throws Exception {
        User user = User.UserBuilder.anUser().build();
        userRepository.save(user);

        mockMvc.perform(delete("/users/" + user.getId()))
                .andExpect(status().isNoContent())
                .andReturn();

        assertThrows(NotFoundException.class, () -> userQueryFacade.getUserById(user.getId()));
    }
}