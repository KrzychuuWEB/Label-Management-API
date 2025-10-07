package pl.krzychuuweb.labelapp.role;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.krzychuuweb.labelapp.IntegrationTestConfig;
import pl.krzychuuweb.labelapp.role.dto.AssignRoleDTO;
import pl.krzychuuweb.labelapp.role.dto.RoleDTO;
import pl.krzychuuweb.labelapp.user.User;
import pl.krzychuuweb.labelapp.user.UserFacade;
import pl.krzychuuweb.labelapp.user.dto.UserCreateDTO;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "email@email.com", authorities = "ROLE_ADMIN")
class RoleControllerIT extends IntegrationTestConfig {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleQueryFacade roleQueryFacade;

    @Autowired
    private UserFacade userFacade;

    @Test
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    void should_assign_role_for_user() throws Exception {
        UserRole userRole = UserRole.ROLE_USER;
        roleRepository.save(Role.RoleBuilder.aRole().withName(userRole).build());
        User user = userFacade.addUser(new UserCreateDTO("exampleFirstName", "email@email.com", "password123456"));
        Role role = roleQueryFacade.getRoleByUserRole(userRole);

        AssignRoleDTO assignRoleDTO = new AssignRoleDTO(userRole.getName(), user.getId());

        MvcResult result = mockMvc.perform(post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assignRoleDTO))
                )
                .andExpect(status().isOk())
                .andReturn();

        List<RoleDTO> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertThat(response).hasSize(1);
        assertThat(response.get(0).name().getName()).isEqualTo(userRole.getName());
        assertThat(response.get(0).id()).isEqualTo(role.getId());
        assertThat(response.get(0).user().id()).isEqualTo(user.getId());
    }
}