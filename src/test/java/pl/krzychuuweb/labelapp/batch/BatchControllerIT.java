package pl.krzychuuweb.labelapp.batch;

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
import pl.krzychuuweb.labelapp.batch.dto.BatchCreateDTO;
import pl.krzychuuweb.labelapp.batch.dto.BatchDTO;
import pl.krzychuuweb.labelapp.batch.dto.BatchEditDTO;
import pl.krzychuuweb.labelapp.exception.BadRequestException;
import pl.krzychuuweb.labelapp.product.Product;
import pl.krzychuuweb.labelapp.product.ProductFacade;
import pl.krzychuuweb.labelapp.product.dto.ProductCreateDTO;
import pl.krzychuuweb.labelapp.user.UserFacade;
import pl.krzychuuweb.labelapp.user.dto.UserCreateDTO;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "email@email.com")
class BatchControllerIT extends IntegrationTestConfig {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private ProductFacade productFacade;

    @Autowired
    private UserFacade userFacade;

    @Test
    @Transactional
    void should_create_new_batch_for_product() throws Exception {
        userFacade.addUser(new UserCreateDTO("firstName", "email@email.com", "password1234567"));
        Product product = productFacade.create(new ProductCreateDTO("name", "description", "composition"));
        BatchCreateDTO batchCreateDTO = new BatchCreateDTO("serial", LocalDate.now().plusDays(1), true, "poland");

        MvcResult response = mockMvc.perform(post("/products/" + product.getId() + "/batches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(batchCreateDTO))
                )
                .andExpect(status().isCreated())
                .andReturn();

        BatchDTO result = objectMapper.readValue(response.getResponse().getContentAsString(), BatchDTO.class);

        assertThat(result.serial()).isEqualTo(batchCreateDTO.serial());
        assertThat(result.expirationDate()).isEqualTo(batchCreateDTO.expirationDate());
        assertTrue(result.isShortDate());
        assertThat(result.country()).isEqualTo(batchCreateDTO.country());
    }

    @Test
    @Transactional
    void should_get_all_batches_by_product() throws Exception {
        userFacade.addUser(new UserCreateDTO("firstName", "email@email.com", "password1234567"));
        Product product = productFacade.create(new ProductCreateDTO("name", "description", "composition"));
        Product product2 = productFacade.create(new ProductCreateDTO("name2", "description2", "composition2"));
        batchRepository.saveAll(List.of(
                Batch.BatchBuilder.aBatch().withProduct(product).withSerial("1234").withCountry("poland").withExpirationDate(LocalDate.now()).withIsShortDate(false).build(),
                Batch.BatchBuilder.aBatch().withProduct(product).withSerial("3214").withCountry("poland").withExpirationDate(LocalDate.now()).withIsShortDate(false).build(),
                Batch.BatchBuilder.aBatch().withProduct(product).withSerial("54745").withCountry("poland").withExpirationDate(LocalDate.now()).withIsShortDate(false).build(),
                Batch.BatchBuilder.aBatch().withProduct(product2).withSerial("2132112").withCountry("poland").withExpirationDate(LocalDate.now()).withIsShortDate(false).build()
        ));

        MvcResult response = mockMvc.perform(get("/products/" + product.getId() + "/batches"))
                .andExpect(status().isOk())
                .andReturn();

        List<BatchDTO> result = objectMapper.readValue(response.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertThat(result).hasSize(3);
    }

    @Test
    @Transactional
    void should_get_batch_by_id() throws Exception {
        userFacade.addUser(new UserCreateDTO("firstName", "email@email.com", "password1234567"));
        Product product = productFacade.create(new ProductCreateDTO("name", "description", "composition"));
        Batch batch = batchRepository.save(
                Batch.BatchBuilder.aBatch().withProduct(product).withSerial("1234").withCountry("poland").withExpirationDate(LocalDate.now()).withIsShortDate(false).build()
        );

        MvcResult response = mockMvc.perform(get("/products/" + product.getId() + "/batches/" + batch.getId()))
                .andExpect(status().isOk())
                .andReturn();

        BatchDTO result = objectMapper.readValue(response.getResponse().getContentAsString(), BatchDTO.class);

        assertThat(result.id()).isEqualTo(batch.getId());
        assertThat(result.serial()).isEqualTo(batch.getSerial());
        assertThat(result.expirationDate()).isEqualTo(batch.getExpirationDate());
        assertThat(result.isShortDate()).isEqualTo(batch.isShortDate());
        assertThat(result.country()).isEqualTo(batch.getCountry());
    }

    @Test
    @Transactional
    void should_edit_batch_with_bad_id_in_url() throws Exception {
        userFacade.addUser(new UserCreateDTO("firstName", "email@email.com", "password1234567"));
        Product product = productFacade.create(new ProductCreateDTO("name", "description", "composition"));
        Batch batch = batchRepository.save(
                Batch.BatchBuilder.aBatch().withProduct(product).withSerial("1234").withCountry("poland").withExpirationDate(LocalDate.now()).withIsShortDate(false).build()
        );
        BatchEditDTO batchEditDTO = new BatchEditDTO(batch.getId() + 1L, "serial", LocalDate.now().plusDays(1), true, "poland");

        mockMvc.perform(put("/products/" + product.getId() + "/batches/" + batch.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(batchEditDTO))
                )
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(BadRequestException.class));
    }

    @Test
    @Transactional
    void should_edit_batch() throws Exception {
        userFacade.addUser(new UserCreateDTO("firstName", "email@email.com", "password1234567"));
        Product product = productFacade.create(new ProductCreateDTO("name", "description", "composition"));
        Batch batch = batchRepository.save(
                Batch.BatchBuilder.aBatch().withProduct(product).withSerial("1234").withCountry("poland").withExpirationDate(LocalDate.now()).withIsShortDate(false).build()
        );
        BatchEditDTO batchEditDTO = new BatchEditDTO(batch.getId(), "new serial", LocalDate.now().plusDays(1), true, "new country");

        MvcResult response = mockMvc.perform(put("/products/" + product.getId() + "/batches/" + batch.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(batchEditDTO))
                )
                .andExpect(status().isOk())
                .andReturn();

        BatchDTO result = objectMapper.readValue(response.getResponse().getContentAsString(), BatchDTO.class);

        assertThat(result.serial()).isEqualTo(batchEditDTO.serial());
        assertThat(result.expirationDate()).isEqualTo(batchEditDTO.expirationDate());
        assertTrue(result.isShortDate());
        assertThat(result.country()).isEqualTo(batchEditDTO.country());
    }
}