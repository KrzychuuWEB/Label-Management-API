package pl.krzychuuweb.labelapp.product;

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
import pl.krzychuuweb.labelapp.product.dto.ProductCreateDTO;
import pl.krzychuuweb.labelapp.product.dto.ProductDTO;
import pl.krzychuuweb.labelapp.product.dto.ProductEditDTO;
import pl.krzychuuweb.labelapp.user.User;
import pl.krzychuuweb.labelapp.user.UserFacade;
import pl.krzychuuweb.labelapp.user.dto.UserCreateDTO;
import pl.krzychuuweb.labelapp.user.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser("email@email.com")
class ProductControllerIT extends IntegrationTestConfig {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserFacade userFacade;

    @Test
    @Transactional
    void should_return_all_products_by_auth_user() throws Exception {
        User authUser = userFacade.addUser(new UserCreateDTO("exampleName", "email@email.com", "password123456"));
        User unAuthUser = userFacade.addUser(new UserCreateDTO("otherUserFirstName", "other@email.com", "password123456"));
        List<Product> products = new ArrayList<>(List.of(
                Product.ProductBuilder.aProduct().withSlug("test").withUser(authUser).build(),
                Product.ProductBuilder.aProduct().withSlug("test2").withUser(authUser).build(),
                Product.ProductBuilder.aProduct().withSlug("test3").withUser(unAuthUser).build()
        ));
        productRepository.saveAll(products);

        MvcResult response = mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andReturn();

        List<ProductDTO> result = objectMapper.readValue(response.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertThat(result).hasSize(2);
    }

    @Test
    @Transactional
    void should_return_product_by_slug_for_auth_user() throws Exception {
        User authUser = userFacade.addUser(new UserCreateDTO("exampleName", "email@email.com", "password123456"));
        Product product = productRepository.save(Product.ProductBuilder.aProduct().withSlug("example-slug").withUser(authUser).build());

        MvcResult response = mockMvc.perform(get("/products/" + product.getSlug()))
                .andExpect(status().isOk())
                .andReturn();

        ProductDTO result = objectMapper.readValue(response.getResponse().getContentAsString(), ProductDTO.class);

        assertThat(result).isInstanceOf(ProductDTO.class);
        assertThat(result.slug()).isEqualTo(product.getSlug());
        assertThat(result.user()).isInstanceOf(UserDTO.class);
        assertThat(result.user().email()).isEqualTo(product.getUser().getEmail());
    }

    @Test
    @Transactional
    void should_create_product_return_product_dto() throws Exception {
        User user = userFacade.addUser(new UserCreateDTO("name", "email@email.com", "password1234556"));
        ProductCreateDTO productCreateDTO = new ProductCreateDTO("example name", "exampleDescription", "exampleComposition");

        MvcResult response = mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productCreateDTO))
                )
                .andExpect(status().isCreated())
                .andReturn();

        ProductDTO result = objectMapper.readValue(response.getResponse().getContentAsString(), ProductDTO.class);

        assertThat(result).isInstanceOf(ProductDTO.class);
        assertThat(result.name()).isEqualTo(productCreateDTO.name());
        assertThat(result.description()).isEqualTo(productCreateDTO.description());
        assertThat(result.composition()).isEqualTo(productCreateDTO.composition());
        assertThat(result.slug()).isEqualTo("example-name");
        assertThat(result.user()).isInstanceOf(UserDTO.class);
        assertThat(result.user().email()).isEqualTo(user.getEmail());
    }

    @Test
    @Transactional
    void should_edit_product_return_exception() throws Exception {
        User user = userFacade.addUser(new UserCreateDTO("name", "email@email.com", "password1234556"));
        Product product = productRepository.save(Product.ProductBuilder.aProduct().withName("example-name").withUser(user).withComposition("example-composition").withDescription("example-description").withSlug("example-slug").build());
        ProductEditDTO productEditDTO = new ProductEditDTO(product.getId() + 1L, "newName", "newDescription", "newComposition");

        mockMvc.perform(put("/products/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productEditDTO))
                )
                .andExpect(ex -> assertThat(ex.getResolvedException()).isInstanceOf(BadRequestException.class));
    }

    @Test
    @Transactional
    void should_edit_product_return_edited_product() throws Exception {
        User user = userFacade.addUser(new UserCreateDTO("name", "email@email.com", "password1234556"));
        Product product = productRepository.save(Product.ProductBuilder.aProduct().withName("example-name").withUser(user).withComposition("example-composition").withDescription("example-description").withSlug("example-slug").build());
        ProductEditDTO productEditDTO = new ProductEditDTO(product.getId(), "new name", "newDescription", "newComposition");

        MvcResult response = mockMvc.perform(put("/products/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productEditDTO))
                )
                .andExpect(status().isOk())
                .andReturn();

        ProductDTO result = objectMapper.readValue(response.getResponse().getContentAsString(), ProductDTO.class);

        assertThat(result.name()).isEqualTo(productEditDTO.name());
        assertThat(result.slug()).isEqualTo("new-name");
        assertThat(result.description()).isEqualTo(productEditDTO.description());
        assertThat(result.composition()).isEqualTo(productEditDTO.composition());
        assertThat(result.user().email()).isEqualTo(user.getEmail());
    }

    @Test
    @Transactional
    void should_delete_product_by_id() throws Exception {
        User user = userFacade.addUser(new UserCreateDTO("name", "email@email.com", "password1234556"));
        Product product = productRepository.save(Product.ProductBuilder.aProduct().withName("exampleName").withSlug("example-slug").withUser(user).build());

        mockMvc.perform(delete("/products/" + product.getId()))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}