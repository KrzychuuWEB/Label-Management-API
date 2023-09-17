package pl.krzychuuweb.labelapp.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.krzychuuweb.labelapp.IntegrationTestConfig;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerIntegrationTest extends IntegrationTestConfig {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserQueryRepository userQueryRepository;

    @Test
    @Transactional
    void should_get_all_users() throws Exception {
        List<User> users = List.of(
                User.UserBuilder.anUser().but().withUsername("username1").withPassword("username1").withEmail("usernam1@email.com").build(),
                User.UserBuilder.anUser().but().withUsername("username2").withPassword("username2").withEmail("usernam2@email.com").build()
        );

        userQueryRepository.saveAll(users);

        MvcResult result = mockMvc.perform(get("/users"))
                .andExpect(status().is(200))
                .andReturn();

        List<User> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertThat(response).hasSize(users.size());
    }

    @Test
    @Transactional
    void should_get_user_by_email() throws Exception {
        User user = User.UserBuilder.anUser().but().withEmail("usernam1@email.com").build();

        userQueryRepository.save(user);

        MvcResult result = mockMvc.perform(get("/users/" + user.getEmail()))
                .andExpect(status().is(200))
                .andReturn();

        User response = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);

        assertThat(response.getEmail()).isEqualTo(user.getEmail());
    }
}