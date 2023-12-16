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
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.CreateNutritionalValueDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.EditNutritionalValueDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.NutritionalValueDTO;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "email@email.com")
class NutritionalValueControllerTest extends IntegrationTestConfig {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NutritionalValueRepository nutritionalValueRepository;

    @Test
    @Transactional
    void should_get_all_nutritional_values() throws Exception {
        nutritionalValueRepository.save(NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withName("Example1").withPriority(1F).build());
        nutritionalValueRepository.save(NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withName("Example2").withPriority(2F).build());
        nutritionalValueRepository.save(NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withName("Example3").withPriority(3F).build());

        MvcResult response = mockMvc.perform(get("/nutritionalvalues"))
                .andExpect(status().isOk())
                .andReturn();

        List<NutritionalValueDTO> result = objectMapper.readValue(response.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertThat(result).hasSize(3);
    }

    @Test
    @Transactional
    void should_add_new_nutritional_value() throws Exception {
        CreateNutritionalValueDTO createNutritionalValueDTO = new CreateNutritionalValueDTO("exampleName", 1F);

        MvcResult response = mockMvc.perform(post("/nutritionalvalues")
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
                        .withPriority(1F)
                        .build()
        );
        EditNutritionalValueDTO editNutritionalValueDTO = new EditNutritionalValueDTO(nutritionalValue.getId(), "newExampleName");

        MvcResult response = mockMvc.perform(put("/nutritionalvalues/" + nutritionalValue.getId())
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
                        .withPriority(1F)
                        .build()
        );

        mockMvc.perform(delete("/nutritionalvalues/" + nutritionalValue.getId()))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}