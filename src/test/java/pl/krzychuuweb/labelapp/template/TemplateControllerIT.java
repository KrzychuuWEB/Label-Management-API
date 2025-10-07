package pl.krzychuuweb.labelapp.template;

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
import pl.krzychuuweb.labelapp.template.dto.TemplateCreateDTO;
import pl.krzychuuweb.labelapp.template.dto.TemplateDTO;
import pl.krzychuuweb.labelapp.template.dto.TemplateEditDTO;
import pl.krzychuuweb.labelapp.user.User;
import pl.krzychuuweb.labelapp.user.UserFacade;
import pl.krzychuuweb.labelapp.user.dto.UserCreateDTO;
import pl.krzychuuweb.labelapp.user.dto.UserDTO;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "email@email.com")
class TemplateControllerIT extends IntegrationTestConfig {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private UserFacade userFacade;

    private final String route = "/templates";

    @Test
    @Transactional
    void should_get_all_templates() throws Exception {
        User user = userFacade.addUser(new UserCreateDTO("firstName", "email@email.com", "password123456677"));
        User user2 = userFacade.addUser(new UserCreateDTO("otherName", "other@email.com", "otherPassword12354"));

        templateRepository.saveAll(
                List.of(
                        Template.TemplateBuilder.aTemplate().withName("name1").withHeight(new BigDecimal(123)).withWidth(new BigDecimal(123)).withUser(user).build(),
                        Template.TemplateBuilder.aTemplate().withName("name2").withHeight(new BigDecimal(123)).withWidth(new BigDecimal(123)).withUser(user).build(),
                        Template.TemplateBuilder.aTemplate().withName("name3").withHeight(new BigDecimal(123)).withWidth(new BigDecimal(123)).withUser(user).build(),
                        Template.TemplateBuilder.aTemplate().withName("name4").withHeight(new BigDecimal(123)).withWidth(new BigDecimal(123)).withUser(user).build(),
                        Template.TemplateBuilder.aTemplate().withName("name5").withHeight(new BigDecimal(123)).withWidth(new BigDecimal(123)).withUser(user2).build()
                )
        );

        MvcResult response = mockMvc.perform(get(route))
                .andExpect(status().isOk())
                .andReturn();

        List<TemplateDTO> result = objectMapper.readValue(response.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertThat(result).hasSize(4);
        assertThat(result.get(0).user().id()).isEqualTo(user.getId());
    }

    @Test
    @Transactional
    void should_get_one_template_by_id() throws Exception {
        User user = userFacade.addUser(new UserCreateDTO("firstName", "email@email.com", "password123456"));
        Template template = templateRepository.save(Template.TemplateBuilder.aTemplate().withUser(user).withName("exampleName").build());

        MvcResult response = mockMvc.perform(get(route + "/" + template.getId()))
                .andExpect(status().isOk())
                .andReturn();

        TemplateDTO result = objectMapper.readValue(response.getResponse().getContentAsString(), TemplateDTO.class);

        assertThat(result.id()).isEqualTo(template.getId());
        assertThat(result.user()).isInstanceOf(UserDTO.class);
        assertThat(result.user().email()).isEqualTo(user.getEmail());
    }

    @Test
    @Transactional
    void should_add_new_template() throws Exception {
        User user = userFacade.addUser(new UserCreateDTO("firstName", "email@email.com", "password123456"));
        TemplateCreateDTO templateCreateDTO = new TemplateCreateDTO("name", new BigDecimal("123"), new BigDecimal("321"));

        MvcResult response = mockMvc.perform(post(route)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(templateCreateDTO))
                )
                .andExpect(status().isCreated())
                .andReturn();

        TemplateDTO result = objectMapper.readValue(response.getResponse().getContentAsString(), TemplateDTO.class);

        assertThat(result.user()).isInstanceOf(UserDTO.class);
        assertThat(result.user().email()).isEqualTo(user.getEmail());
        assertThat(result.name()).isEqualTo(templateCreateDTO.name());
        assertThat(result.height()).isEqualTo(templateCreateDTO.height());
        assertThat(result.width()).isEqualTo(templateCreateDTO.width());
    }

    @Test
    @Transactional
    void should_edit_template() throws Exception {
        User user = userFacade.addUser(new UserCreateDTO("firstName", "email@email.com", "password123456"));
        Template template = templateRepository.save(Template.TemplateBuilder.aTemplate().withUser(user).withName("name").withWidth(new BigDecimal("123")).withHeight(new BigDecimal("321")).build());
        TemplateEditDTO templateEditDTO = new TemplateEditDTO(template.getId(), "newName", new BigDecimal("321.2"), new BigDecimal("123.3"));

        MvcResult response = mockMvc.perform(put(route + "/" + template.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(templateEditDTO))
                )
                .andExpect(status().isOk())
                .andReturn();

        TemplateDTO result = objectMapper.readValue(response.getResponse().getContentAsString(), TemplateDTO.class);
        assertThat(result.user()).isInstanceOf(UserDTO.class);
        assertThat(result.user().email()).isEqualTo(user.getEmail());
        assertThat(result.name()).isEqualTo(templateEditDTO.name());
        assertThat(result.height()).isEqualTo(templateEditDTO.height());
        assertThat(result.width()).isEqualTo(templateEditDTO.width());
    }

    @Test
    @Transactional
    void should_edit_template_expected_bad_request_exception_because_id_is_not_same() throws Exception {
        User user = userFacade.addUser(new UserCreateDTO("firstName", "email@email.com", "password123456"));
        Template template = templateRepository.save(Template.TemplateBuilder.aTemplate().withUser(user).withName("name").withWidth(new BigDecimal("123")).withHeight(new BigDecimal("321")).build());
        TemplateEditDTO templateEditDTO = new TemplateEditDTO(template.getId() + 1L, "newName", new BigDecimal("321"), new BigDecimal("123"));

        mockMvc.perform(put(route + "/" + template.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(templateEditDTO))
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(BadRequestException.class))
                .andReturn();
    }

    @Test
    @Transactional
    void should_delete_template_by_id() throws Exception {
        User user = userFacade.addUser(new UserCreateDTO("firstName", "email@email.com", "password123456"));
        Template template = templateRepository.save(Template.TemplateBuilder.aTemplate().withUser(user).build());

        mockMvc.perform(delete(route + "/" + template.getId()))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}