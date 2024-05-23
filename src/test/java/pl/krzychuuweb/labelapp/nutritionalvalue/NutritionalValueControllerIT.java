package pl.krzychuuweb.labelapp.nutritionalvalue;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import pl.krzychuuweb.labelapp.IntegrationTestConfig;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.NutritionalValueCreateDTO;
import pl.krzychuuweb.labelapp.user.UserFacade;
import pl.krzychuuweb.labelapp.user.dto.UserCreateDTO;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "email@email.com", roles = {"ADMIN"})
class NutritionalValueControllerIT extends IntegrationTestConfig {

    private final String basicRoute = "/nutritional-values";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private NutritionalValueRepository nutritionalValueRepository;

    @BeforeEach
    void setUp() {
        userFacade.addUser(new UserCreateDTO("firstName", "email@email.com", "password12345"));
    }

    @Test
    @Transactional
    void should_create_new_nutritional_value() throws Exception {
        NutritionalValueCreateDTO nutritionalValueCreateDTO = new NutritionalValueCreateDTO("exmaple name");

        MvcResult response = mockMvc.perform(post(basicRoute)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nutritionalValueCreateDTO))
                )
                .andExpect(status().isCreated())
                .andReturn();

        NutritionalValue result = objectMapper.readValue(response.getResponse().getContentAsString(), NutritionalValue.class);

        assertThat(result.getName()).isEqualTo(nutritionalValueCreateDTO.name());
        assertThat(result.getPriority()).isEqualTo(new BigDecimal("1.0"));
    }

    @Test
    @Transactional
    void should_get_all_nutritional_values() throws Exception {
        List<NutritionalValue> list = List.of(
                NutritionalValueTestBuilder.aNutritionalValue().withId(1L).build(),
                NutritionalValueTestBuilder.aNutritionalValue().withId(2L).build(),
                NutritionalValueTestBuilder.aNutritionalValue().withId(3L).build(),
                NutritionalValueTestBuilder.aNutritionalValue().withId(4L).build()
        );
        nutritionalValueRepository.saveAll(list);

        MvcResult response = mockMvc.perform(get(basicRoute))
                .andExpect(status().isOk())
                .andReturn();

        List<NutritionalValue> result = objectMapper.readValue(response.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertThat(result).hasSameSizeAs(list);
    }
}