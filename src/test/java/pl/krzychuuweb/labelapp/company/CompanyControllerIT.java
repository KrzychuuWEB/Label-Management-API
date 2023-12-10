package pl.krzychuuweb.labelapp.company;

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
import pl.krzychuuweb.labelapp.company.dto.CompanyCreateDTO;
import pl.krzychuuweb.labelapp.company.dto.CompanyDTO;
import pl.krzychuuweb.labelapp.company.dto.CompanyEditDTO;
import pl.krzychuuweb.labelapp.exceptions.BadRequestException;
import pl.krzychuuweb.labelapp.user.User;
import pl.krzychuuweb.labelapp.user.UserFacade;
import pl.krzychuuweb.labelapp.user.dto.UserCreateDTO;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "email@email.com")
class CompanyControllerIT extends IntegrationTestConfig {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private CompanyFacade companyFacade;

    @Test
    @Transactional
    void should_get_company_by_id() throws Exception {
        User user = userFacade.addUser(new UserCreateDTO("firstName", "email@email.com", "secret123456"));
        Company company = companyRepository.save(Company.CompanyBuilder.aCompany().withName("companyName").withFooter("companyFooter").withUser(user).build());

        MvcResult result = mockMvc.perform(get("/companies/" + company.getId()))
                .andExpect(status().isOk())
                .andReturn();

        CompanyDTO response = objectMapper.readValue(result.getResponse().getContentAsString(), CompanyDTO.class);

        assertThat(response.name()).isEqualTo(company.getName());
        assertThat(response.user().id()).isEqualTo(user.getId());
    }

    @Test
    @Transactional
    void should_get_all_companies() throws Exception {
        User user = userFacade.addUser(new UserCreateDTO("firstName", "email@email.com", "password1234"));
        List<Company> companyList = List.of(
                Company.CompanyBuilder.aCompany().withUser(user).build(),
                Company.CompanyBuilder.aCompany().withUser(user).build(),
                Company.CompanyBuilder.aCompany().withUser(user).build()
        );
        companyRepository.saveAll(companyList);

        MvcResult result = mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andReturn();

        List<CompanyDTO> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertThat(response).hasSize(companyList.size());
    }

    @Test
    @Transactional
    void should_create_company() throws Exception {
        userFacade.addUser(new UserCreateDTO("firstName", "email@email.com", "password1234"));
        CompanyCreateDTO companyCreateDTO = new CompanyCreateDTO("companyName", "companyFooter");

        MvcResult result = mockMvc.perform(post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(companyCreateDTO))
                )
                .andExpect(status().isCreated())
                .andReturn();
        CompanyDTO response = objectMapper.readValue(result.getResponse().getContentAsString(), CompanyDTO.class);

        assertThat(response.name()).isEqualTo(companyCreateDTO.name());
        assertThat(response.footer()).isEqualTo(companyCreateDTO.footer());
    }

    @Test
    @Transactional
    void should_edit_company() throws Exception {
        User user = userFacade.addUser(new UserCreateDTO("firstName", "email@email.com", "password1234"));
        Company company = companyRepository.save(Company.CompanyBuilder.aCompany().withName("companyName").withFooter("companyFooter").withUser(user).build());
        String oldCompanyName = company.getName();
        CompanyEditDTO companyEditDTO = new CompanyEditDTO(company.getId(), "newCompanyName", "newCompanyFooter");

        MvcResult result = mockMvc.perform(put("/companies/" + companyEditDTO.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(companyEditDTO))
                )
                .andExpect(status().isOk())
                .andReturn();
        CompanyDTO response = objectMapper.readValue(result.getResponse().getContentAsString(), CompanyDTO.class);

        assertThat(response.name()).isEqualTo(companyEditDTO.name());
        assertThat(response.footer()).isEqualTo(companyEditDTO.footer());

        assertThat(response.name()).isNotEqualTo(oldCompanyName);
    }

    @Test
    @Transactional
    void should_edit_company_return_badRequest_exception() throws Exception {
        userFacade.addUser(new UserCreateDTO("firstName", "email@email.com", "password123456"));
        Company company = companyFacade.addCompany(new CompanyCreateDTO("exampleName", "exampleFooter"));
        CompanyEditDTO companyEditDTO = new CompanyEditDTO(company.getId() + 1L, "companyName", "companyFooter");

        mockMvc.perform(put("/companies/" + company.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(companyEditDTO))
                )
                .andExpect(ex -> assertThat(ex.getResolvedException()).isInstanceOf(BadRequestException.class))
                .andReturn();
    }

    @Test
    @Transactional
    void should_delete_company() throws Exception {
        User user = userFacade.addUser(new UserCreateDTO("firstName", "email@email.com", "password1234"));
        Company company = companyRepository.save(Company.CompanyBuilder.aCompany().withUser(user).build());

        assertThat(company).isInstanceOf(Company.class);

        mockMvc.perform(delete("/companies/" + company.getId()))
                .andExpect(status().isNoContent())
                .andReturn();
    }


}