package pl.krzychuuweb.labelapp.initial;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.krzychuuweb.labelapp.IntegrationTestConfig;
import pl.krzychuuweb.labelapp.exception.BadRequestException;
import pl.krzychuuweb.labelapp.initial.dto.InitialCreateDTO;
import pl.krzychuuweb.labelapp.initial.dto.InitialEditDTO;
import pl.krzychuuweb.labelapp.user.User;
import pl.krzychuuweb.labelapp.user.UserFacade;
import pl.krzychuuweb.labelapp.user.dto.UserCreateDTO;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "email@email.com")
class InitialControllerIT extends IntegrationTestConfig {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InitialRepository initialRepository;

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private InitialFacade initialFacade;

    @Test
    @Transactional
    void should_get_all_initials_for_logged_user() throws Exception {
        User user = userFacade.addUser(new UserCreateDTO("firstName", "email@email.com", "password1234556"));
        User badUser = userFacade.addUser(new UserCreateDTO("firstName", "bad@email.com", "password1234556"));
        List<Initial> list = List.of(
                Initial.InitialBuilder.anInitial().withUser(user).build(),
                Initial.InitialBuilder.anInitial().withUser(user).build(),
                Initial.InitialBuilder.anInitial().withUser(user).build(),
                Initial.InitialBuilder.anInitial().withUser(badUser).build()
        );
        initialRepository.saveAll(list);


        MvcResult response = mockMvc.perform(get("/initials"))
                .andExpect(status().isOk())
                .andReturn();

        List<Initial> result = objectMapper.readValue(response.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertThat(result).hasSize(3);
    }

    @Test
    @Transactional
    void should_add_new_initial() throws Exception {
        InitialCreateDTO initialCreateDTO = new InitialCreateDTO("firstName", "lastName", "FL");
        User user = userFacade.addUser(new UserCreateDTO("firstName", "email@email.com", "password123456"));

        MvcResult response = mockMvc.perform(post("/initials")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(initialCreateDTO))
                )
                .andExpect(status().isCreated())
                .andReturn();

        Initial result = objectMapper.readValue(response.getResponse().getContentAsString(), Initial.class);

        assertThat(result.getName()).isEqualTo(initialCreateDTO.name());
        assertThat(result.getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    @Transactional
    void should_add_new_initial_but_name_is_used() throws Exception {
        InitialCreateDTO initialCreateDTO = new InitialCreateDTO("firstName", "lastName", "FL");
        User user = userFacade.addUser(new UserCreateDTO("firstName", "email@email.com", "password123456"));
        initialRepository.save(Initial.InitialBuilder.anInitial().withName(initialCreateDTO.name()).withUser(user).build());

        mockMvc.perform(post("/initials")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(initialCreateDTO))
                )
                .andExpect(status().isBadRequest())
                .andExpect(ex -> assertThat(ex.getResolvedException()).isInstanceOf(BadRequestException.class))
                .andReturn();
    }

    @Test
    @Transactional
    void should_edit_initial_but_initial_id_is_not_same() throws Exception {
        userFacade.addUser(new UserCreateDTO("firstName", "email@email.com", "password123456"));
        Initial initial = initialFacade.add(new InitialCreateDTO("firstName", "lastName", "name"));
        InitialEditDTO initialEditDTO = new InitialEditDTO(initial.getId() + 1L, "newFirstName", "newLastName", "newName");

        mockMvc.perform(put("/initials/" + initial.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(initialEditDTO))
                )
                .andExpect(status().isBadRequest())
                .andExpect(ex -> assertThat(ex.getResolvedException()).isInstanceOf(BadRequestException.class))
                .andReturn();
    }


    @Test
    @Transactional
    void should_edit_initial() throws Exception {
        User user = userFacade.addUser(new UserCreateDTO("firstName", "email@email.com", "password123456"));
        Initial initial = initialRepository.save(Initial.InitialBuilder.anInitial().withFirstName("firstName").withLastName("lastName").withName("name").withUser(user).build());
        InitialEditDTO initialEditDTO = new InitialEditDTO(initial.getId(), "newFirstName", "newLastName", "newName");

        MvcResult response = mockMvc.perform(put("/initials/" + initial.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(initialEditDTO))
                )
                .andExpect(status().isOk())
                .andReturn();

        Initial result = objectMapper.readValue(response.getResponse().getContentAsString(), Initial.class);

        assertThat(result.getName()).isEqualTo(initialEditDTO.name());
        assertThat(result.getFirstName()).isEqualTo(initialEditDTO.firstName());
        assertThat(result.getLastName()).isEqualTo(initialEditDTO.lastName());
        assertThat(result.getUser().getId()).isEqualTo(user.getId());
    }

    @Test
    @Transactional
    void should_delete_initial() throws Exception {
        User user = userFacade.addUser(new UserCreateDTO("firstName", "email@email.com", "password123456"));
        Initial initial = initialRepository.save(Initial.InitialBuilder.anInitial().withFirstName("firstName").withLastName("lastName").withName("name").withUser(user).build());

        mockMvc.perform(delete("/initials/" + initial.getId()))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}