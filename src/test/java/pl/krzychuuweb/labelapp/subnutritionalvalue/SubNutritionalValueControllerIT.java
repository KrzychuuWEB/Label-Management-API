package pl.krzychuuweb.labelapp.subnutritionalvalue;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.krzychuuweb.labelapp.IntegrationTestConfig;
import pl.krzychuuweb.labelapp.exceptions.BadRequestException;
import pl.krzychuuweb.labelapp.exceptions.NotFoundException;
import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValue;
import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValueFacade;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.CreateNutritionalValueDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.EditNutritionalValueDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "email@email.com")
class SubNutritionalValueControllerIT extends IntegrationTestConfig {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SubNutritionalValueRepository subNutritionalValueRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NutritionalValueFacade nutritionalValueFacade;

    @Autowired
    private SubNutritionalValueQueryFacade subNutritionalValueQueryFacade;

    @Test
    @Transactional
    void should_add_new_sub_nutritional_value() throws Exception {
        CreateNutritionalValueDTO createNutritionalValueDTO = new CreateNutritionalValueDTO("exampleName", 1);
        NutritionalValue nutritionalValue = nutritionalValueFacade.add(
                new CreateNutritionalValueDTO("parent", 1)
        );

        MvcResult response = mockMvc.perform(post("/nutritional-values/" + nutritionalValue.getId() + "/sub")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createNutritionalValueDTO))
                )
                .andExpect(status().isCreated())
                .andReturn();

        SubNutritionalValue result = objectMapper.readValue(response.getResponse().getContentAsString(), SubNutritionalValue.class);

        assertThat(result.getName()).isEqualTo(createNutritionalValueDTO.name());
        assertThat(result.getPriority()).isEqualTo(createNutritionalValueDTO.priority());
    }

    @Test
    void should_edit_sub_nutritional_value_throw_bad_request_exception() throws Exception {
        EditNutritionalValueDTO editNutritionalValueDTO = new EditNutritionalValueDTO(1L, "anyName");

        mockMvc.perform(put("/nutritional-values/sub/" + editNutritionalValueDTO.id() + 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editNutritionalValueDTO))
                )
                .andExpect(ex -> assertThat(ex.getResolvedException()).isInstanceOf(BadRequestException.class))
                .andReturn();
    }

    @Test
    @Transactional
    void should_edit_sub_nutritional_value() throws Exception {
        SubNutritionalValue subNutritionalValue = subNutritionalValueRepository.save(
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue()
                        .withName("oldName")
                        .withPriority(1)
                        .build()
        );
        EditNutritionalValueDTO editNutritionalValueDTO = new EditNutritionalValueDTO(subNutritionalValue.getId(), "newExampleName");

        MvcResult response = mockMvc.perform(put("/nutritional-values/sub/" + editNutritionalValueDTO.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editNutritionalValueDTO))
                )
                .andExpect(status().isOk())
                .andReturn();

        SubNutritionalValue result = objectMapper.readValue(response.getResponse().getContentAsString(), SubNutritionalValue.class);

        assertThat(result.getId()).isEqualTo(subNutritionalValue.getId());
        assertThat(result.getName()).isEqualTo(editNutritionalValueDTO.name());
    }

    @Test
    @Transactional
    void should_delete_sub_nutritional_value_by_id() throws Exception {
        SubNutritionalValue subNutritionalValue = subNutritionalValueRepository.save(
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue()
                        .withName("exampleName")
                        .withPriority(1)
                        .build()
        );

        mockMvc.perform(delete("/nutritional-values/sub/" + subNutritionalValue.getId()))
                .andExpect(status().isNoContent())
                .andReturn();

        assertThatThrownBy(() -> subNutritionalValueQueryFacade.getById(subNutritionalValue.getId())).isInstanceOf(NotFoundException.class);
    }
}