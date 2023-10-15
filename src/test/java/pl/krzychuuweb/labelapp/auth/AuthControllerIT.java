package pl.krzychuuweb.labelapp.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.krzychuuweb.labelapp.IntegrationTestConfig;
import pl.krzychuuweb.labelapp.auth.dto.AuthDTO;
import pl.krzychuuweb.labelapp.auth.dto.LoginDTO;
import pl.krzychuuweb.labelapp.user.UserFacade;
import pl.krzychuuweb.labelapp.user.dto.UserCreateDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerIT extends IntegrationTestConfig {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserFacade userFacade;

    @Test
    @Transactional
    void should_return_jwt_token() throws Exception {
        UserCreateDTO userCreateDTO = new UserCreateDTO("firstName", "email@email.com", "password12345");
        userFacade.addUser(userCreateDTO);
        LoginDTO loginDTO = new LoginDTO(userCreateDTO.email(), userCreateDTO.password());

        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO))
                )
                .andExpect(status().isOk())
                .andReturn();

        AuthDTO response = objectMapper.readValue(result.getResponse().getContentAsString(), AuthDTO.class);

        assertThat(response.token()).isNotNull();
    }

    @Test
    @Transactional
    void should_register_user() throws Exception {
        UserCreateDTO userCreateDTO = new UserCreateDTO("firstName", "email@email.com", "password12345");

        MvcResult result = mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateDTO))
                )
                .andExpect(status().isOk())
                .andReturn();

        AuthDTO response = objectMapper.readValue(result.getResponse().getContentAsString(), AuthDTO.class);

        assertThat(response.token()).isNotNull();
    }
}
