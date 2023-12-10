package pl.krzychuuweb.labelapp.security.ownership;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.krzychuuweb.labelapp.IntegrationTestConfig;
import pl.krzychuuweb.labelapp.company.Company;
import pl.krzychuuweb.labelapp.company.CompanyFacade;
import pl.krzychuuweb.labelapp.company.dto.CompanyCreateDTO;
import pl.krzychuuweb.labelapp.user.User;
import pl.krzychuuweb.labelapp.user.UserFacade;
import pl.krzychuuweb.labelapp.user.dto.UserCreateDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "email@email.com")
class SecurityOwnershipControllerIT extends IntegrationTestConfig {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyFacade companyFacade;

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Long invalidUserId = 0L;

    @BeforeEach
    void setUp() {
        userFacade.addUser(new UserCreateDTO("firstName", "email@email.com", "secret123456"));
        User invalidUser = userFacade.addUser(new UserCreateDTO("firstName", "invalid@email.com", "secret123456"));
        this.invalidUserId = invalidUser.getId();
    }

    @Test
    @Transactional
    void should_user_not_has_access_for_company_resource() throws Exception {
        jdbcTemplate.execute("INSERT INTO companies (name, footer, user_id) VALUES ('value1', 'value2', '" + this.invalidUserId + "')");

        mockMvc.perform(get("/companies/" + 1))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @Transactional
    void should_user_has_access_for_company_resource() throws Exception {
        Company company = companyFacade.addCompany(new CompanyCreateDTO("companyName", "companyFooter"));

        mockMvc.perform(get("/companies/" + company.getId()))
                .andExpect(status().isOk())
                .andReturn();
    }
}
