package pl.krzychuuweb.labelapp.nutritionalvalue;

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
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.ChangeNutritionalValuePriorityDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.CreateNutritionalValueDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.EditNutritionalValueDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.NutritionalValueDTO;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "email@email.com")
class NutritionalValueControllerIT extends IntegrationTestConfig {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NutritionalValueRepository nutritionalValueRepository;

    @Autowired
    private NutritionalValueFacade nutritionalValueFacade;

    @Test
    @Transactional
    void should_get_all_nutritional_values() throws Exception {
        nutritionalValueRepository.save(NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withName("Example1").withPriority(1).build());
        nutritionalValueRepository.save(NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withName("Example2").withPriority(2).build());
        nutritionalValueRepository.save(NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withName("Example3").withPriority(3).build());

        MvcResult response = mockMvc.perform(get("/nutritional-values"))
                .andExpect(status().isOk())
                .andReturn();

        List<NutritionalValueDTO> result = objectMapper.readValue(response.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertThat(result).hasSize(3);
    }

    @Test
    @Transactional
    void should_add_new_nutritional_value() throws Exception {
        CreateNutritionalValueDTO createNutritionalValueDTO = new CreateNutritionalValueDTO("exampleName", 1);

        MvcResult response = mockMvc.perform(post("/nutritional-values")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createNutritionalValueDTO))
                )
                .andExpect(status().isCreated())
                .andReturn();

        NutritionalValueDTO result = objectMapper.readValue(response.getResponse().getContentAsString(), NutritionalValueDTO.class);

        assertThat(result.name()).isEqualTo(createNutritionalValueDTO.name());
        assertThat(result.priority()).isEqualTo(createNutritionalValueDTO.priority());
    }

    @Test
    @Transactional
    void should_edit_nutritional_value() throws Exception {
        NutritionalValue nutritionalValue = nutritionalValueRepository.save(
                NutritionalValue.NutritionalValueBuilder
                        .aNutritionalValue()
                        .withName("exampleName")
                        .withPriority(1)
                        .build()
        );
        EditNutritionalValueDTO editNutritionalValueDTO = new EditNutritionalValueDTO(nutritionalValue.getId(), "newExampleName");

        MvcResult response = mockMvc.perform(put("/nutritional-values/" + nutritionalValue.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editNutritionalValueDTO))
                )
                .andExpect(status().isOk())
                .andReturn();

        NutritionalValueDTO result = objectMapper.readValue(response.getResponse().getContentAsString(), NutritionalValueDTO.class);

        assertThat(result.name()).isEqualTo(editNutritionalValueDTO.name());
    }

    @Test
    @Transactional
    void should_delete_nutritional_value() throws Exception {
        NutritionalValue nutritionalValue = nutritionalValueRepository.save(
                NutritionalValue.NutritionalValueBuilder
                        .aNutritionalValue()
                        .withName("exampleName")
                        .withPriority(1)
                        .build()
        );

        mockMvc.perform(delete("/nutritional-values/" + nutritionalValue.getId()))
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    @Transactional
    void should_edit_nutritional_value_priority() throws Exception {
        NutritionalValue nutritionalValue1 = nutritionalValueFacade.add(new CreateNutritionalValueDTO("one", 1));
        NutritionalValue nutritionalValue2 = nutritionalValueFacade.add(new CreateNutritionalValueDTO("two", 2));
        NutritionalValue nutritionalValue3 = nutritionalValueFacade.add(new CreateNutritionalValueDTO("three", 3));
        NutritionalValue nutritionalValue4 = nutritionalValueFacade.add(new CreateNutritionalValueDTO("four", 4));
        NutritionalValue nutritionalValue5 = nutritionalValueFacade.add(new CreateNutritionalValueDTO("five", 5));
        ChangeNutritionalValuePriorityDTO changeNutritionalValuePriorityDTO = new ChangeNutritionalValuePriorityDTO(nutritionalValue2.getId(), 4);

        MvcResult response = mockMvc.perform(put("/nutritional-values/" + nutritionalValue2.getId() + "/priority")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changeNutritionalValuePriorityDTO))
                )
                .andExpect(status().isOk())
                .andReturn();

        List<NutritionalValueDTO> result = objectMapper.readValue(response.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertThat(getNutritionalValueFromList(result, nutritionalValue1.getId()).priority()).isEqualTo(1);
        assertThat(getNutritionalValueFromList(result, nutritionalValue2.getId()).priority()).isEqualTo(changeNutritionalValuePriorityDTO.priority());
        assertThat(getNutritionalValueFromList(result, nutritionalValue3.getId()).priority()).isEqualTo(2);
        assertThat(getNutritionalValueFromList(result, nutritionalValue4.getId()).priority()).isEqualTo(3);
        assertThat(getNutritionalValueFromList(result, nutritionalValue5.getId()).priority()).isEqualTo(5);
    }

    @Test
    void should_edit_nutritional_value_priority_but_priority_is_not_same() throws Exception {
        ChangeNutritionalValuePriorityDTO changeNutritionalValuePriorityDTO = new ChangeNutritionalValuePriorityDTO(2L, 4);

        mockMvc.perform(put("/nutritional-values/" + (changeNutritionalValuePriorityDTO.id() + 1L) + "/priority")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changeNutritionalValuePriorityDTO))
                )
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(BadRequestException.class));
    }

    private NutritionalValueDTO getNutritionalValueFromList(final List<NutritionalValueDTO> list, final Long nutritionalId) {
        return list.stream().filter(nv -> nv.id().equals(nutritionalId)).findFirst().orElse(null);
    }
}