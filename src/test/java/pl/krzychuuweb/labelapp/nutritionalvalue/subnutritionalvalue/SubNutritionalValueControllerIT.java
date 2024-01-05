package pl.krzychuuweb.labelapp.nutritionalvalue.subnutritionalvalue;

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
import pl.krzychuuweb.labelapp.exception.NotFoundException;
import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValue;
import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValueFacade;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.ChangeNutritionalValuePriorityDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.CreateNutritionalValueDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.EditNutritionalValueDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.subnutritionalvalue.dto.SubNutritionalValueDTO;

import java.util.List;

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

    @Autowired
    private SubNutritionalValueFacade subNutritionalValueFacade;

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

    @Test
    @Transactional
    void should_edit_sub_nutritional_value_priority() throws Exception {
        NutritionalValue nutritionalValue = nutritionalValueFacade.add(new CreateNutritionalValueDTO("parent", 1));
        SubNutritionalValue nutritionalValue1 = subNutritionalValueFacade.add(new CreateNutritionalValueDTO("one", 1), nutritionalValue.getId());
        SubNutritionalValue nutritionalValue2 = subNutritionalValueFacade.add(new CreateNutritionalValueDTO("two", 2), nutritionalValue.getId());
        SubNutritionalValue nutritionalValue3 = subNutritionalValueFacade.add(new CreateNutritionalValueDTO("three", 3), nutritionalValue.getId());
        SubNutritionalValue nutritionalValue4 = subNutritionalValueFacade.add(new CreateNutritionalValueDTO("four", 4), nutritionalValue.getId());
        SubNutritionalValue nutritionalValue5 = subNutritionalValueFacade.add(new CreateNutritionalValueDTO("five", 5), nutritionalValue.getId());
        ChangeNutritionalValuePriorityDTO changeNutritionalValuePriorityDTO = new ChangeNutritionalValuePriorityDTO(nutritionalValue2.getId(), 4);

        MvcResult response = mockMvc.perform(put("/nutritional-values/sub/" + nutritionalValue2.getId() + "/priority")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changeNutritionalValuePriorityDTO))
                )
                .andExpect(status().isOk())
                .andReturn();

        List<SubNutritionalValueDTO> result = objectMapper.readValue(response.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertThat(getNutritionalValueFromList(result, nutritionalValue1.getId()).priority()).isEqualTo(1);
        assertThat(getNutritionalValueFromList(result, nutritionalValue2.getId()).priority()).isEqualTo(changeNutritionalValuePriorityDTO.priority());
        assertThat(getNutritionalValueFromList(result, nutritionalValue3.getId()).priority()).isEqualTo(2);
        assertThat(getNutritionalValueFromList(result, nutritionalValue4.getId()).priority()).isEqualTo(3);
        assertThat(getNutritionalValueFromList(result, nutritionalValue5.getId()).priority()).isEqualTo(5);
    }

    @Test
    void should_edit_sub_nutritional_value_priority_but_priority_is_not_same() throws Exception {
        ChangeNutritionalValuePriorityDTO changeNutritionalValuePriorityDTO = new ChangeNutritionalValuePriorityDTO(2L, 4);

        mockMvc.perform(put("/nutritional-values/sub/" + (changeNutritionalValuePriorityDTO.id() + 1L) + "/priority")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changeNutritionalValuePriorityDTO))
                )
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(BadRequestException.class));
    }

    private SubNutritionalValueDTO getNutritionalValueFromList(final List<SubNutritionalValueDTO> list, final Long nutritionalId) {
        return list.stream().filter(nv -> nv.id().equals(nutritionalId)).findFirst().orElse(null);
    }
}