package pl.krzychuuweb.labelapp.batchnutritionalmapping;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.krzychuuweb.labelapp.IntegrationTestConfig;
import pl.krzychuuweb.labelapp.batch.Batch;
import pl.krzychuuweb.labelapp.batch.BatchFacade;
import pl.krzychuuweb.labelapp.batch.dto.BatchCreateDTO;
import pl.krzychuuweb.labelapp.batchnutritionalmapping.dto.BatchNutritionalMappingCreateDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValueFacade;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.CreateNutritionalValueDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.NutritionalValueUseDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.subnutritionalvalue.SubNutritionalValueFacade;
import pl.krzychuuweb.labelapp.product.Product;
import pl.krzychuuweb.labelapp.product.ProductFacade;
import pl.krzychuuweb.labelapp.product.dto.ProductCreateDTO;
import pl.krzychuuweb.labelapp.user.UserFacade;
import pl.krzychuuweb.labelapp.user.dto.UserCreateDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "email@email.com")
class BatchNutritionalMappingControllerIT extends IntegrationTestConfig {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BatchNutritionalMappingFacade batchNutritionalMappingFacade;

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private ProductFacade productFacade;

    @Autowired
    private BatchFacade batchFacade;

    @Autowired
    private NutritionalValueFacade nutritionalValueFacade;

    @Autowired
    private SubNutritionalValueFacade subNutritionalValueFacade;

    private List<Long> nutritionalIds = new ArrayList<>();
    private List<Long> subNutritionalIds = new ArrayList<>();
    private List<NutritionalValueUseDTO> nutritionalValueDTO = new ArrayList<>();
    private List<NutritionalValueUseDTO> subNutritionalValueDTO = new ArrayList<>();

    @BeforeEach
    void setUp() {
        nutritionalIds.add(nutritionalValueFacade.add(new CreateNutritionalValueDTO("example1", 1)).getId());
        nutritionalIds.add(nutritionalValueFacade.add(new CreateNutritionalValueDTO("example2", 2)).getId());
        nutritionalIds.add(nutritionalValueFacade.add(new CreateNutritionalValueDTO("example3", 3)).getId());
        subNutritionalIds.add(subNutritionalValueFacade.add(new CreateNutritionalValueDTO("example1", 1), nutritionalIds.get(0)).getId());
        subNutritionalIds.add(subNutritionalValueFacade.add(new CreateNutritionalValueDTO("example2", 2), nutritionalIds.get(1)).getId());
        subNutritionalIds.add(subNutritionalValueFacade.add(new CreateNutritionalValueDTO("example3", 3), nutritionalIds.get(2)).getId());

        nutritionalValueDTO.addAll(List.of(
                new NutritionalValueUseDTO(nutritionalIds.get(0), "example value1"),
                new NutritionalValueUseDTO(nutritionalIds.get(2), "example value3"),
                new NutritionalValueUseDTO(nutritionalIds.get(1), "example value2")

        ));
        subNutritionalValueDTO.addAll(List.of(
                new NutritionalValueUseDTO(subNutritionalIds.get(0), "example value1"),
                new NutritionalValueUseDTO(subNutritionalIds.get(1), "example value3"),
                new NutritionalValueUseDTO(subNutritionalIds.get(2), "example value2")
        ));
    }

    @AfterEach
    void clear() {
        nutritionalIds.clear();
        subNutritionalIds.clear();
        nutritionalValueDTO.clear();
        subNutritionalValueDTO.clear();
    }

    @Test
    @Transactional
    void should_add_new_nutritional_values_for_batch() throws Exception {
        userFacade.addUser(new UserCreateDTO("first name", "email@email.com", "password12345"));
        Product product = productFacade.create(new ProductCreateDTO("example name", "example description", "example composition"));
        Batch batch = batchFacade.create(new BatchCreateDTO("example serial", LocalDate.now().plusDays(1), false, "example country"), product.getId());
        BatchNutritionalMappingCreateDTO mappingCreateDTO = new BatchNutritionalMappingCreateDTO(
                nutritionalValueDTO,
                new ArrayList<>()
        );

        MvcResult response = mockMvc.perform(
                        post("/products/" + product.getId() + "/batches/" + batch.getId() + "/nutritionals")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(mappingCreateDTO))
                )
                .andExpect(status().isCreated())
                .andReturn();

        List<BatchNutritionalMapping> result = objectMapper.readValue(response.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertThat(result).hasSameSizeAs(nutritionalValueDTO);
    }
}