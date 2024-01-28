package pl.krzychuuweb.labelapp.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.krzychuuweb.labelapp.auth.AuthQueryFacade;
import pl.krzychuuweb.labelapp.exception.BadRequestException;
import pl.krzychuuweb.labelapp.product.dto.ProductCreateDTO;
import pl.krzychuuweb.labelapp.product.dto.ProductEditDTO;
import pl.krzychuuweb.labelapp.user.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductFacadeImplTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductQueryFacade productQueryFacade;

    @Mock
    private AuthQueryFacade authQueryFacade;

    @Mock
    private ProductFactory productFactory;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductFacadeImpl productFacade;

    @Test
    void should_return_new_product() {
        User user = User.UserBuilder.anUser().withEmail("email@email.com").build();
        ProductCreateDTO productCreateDTO = new ProductCreateDTO("example name", "exampleDescription", "exampleComposition");
        Product product = Product.ProductBuilder.aProduct()
                .withName(productCreateDTO.name())
                .withDescription(productCreateDTO.description())
                .withComposition(productCreateDTO.composition())
                .withUser(user)
                .withSlug("example-name")
                .build();

        when(productQueryFacade.checkWhetherNameInResourceIsNotUsedByLoggedUser(anyString())).thenReturn(true);
        when(authQueryFacade.getLoggedUser()).thenReturn(user);
        when(productFactory.createProduct(any(ProductCreateDTO.class), any(User.class))).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productFacade.create(productCreateDTO);

        assertThat(result).isInstanceOf(Product.class);
        assertThat(result.getName()).isEqualTo(product.getName());
        assertThat(result.getSlug()).isEqualTo(product.getSlug());
        assertThat(result.getUser()).isInstanceOf(User.class);
        assertThat(result.getUser().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void should_return_edit_product() {
        ProductEditDTO productEditDTO = new ProductEditDTO(1L, "new name", "newDescription", "newComposition");
        Product product = Product.ProductBuilder.aProduct().withName("example-name").withComposition("example-composition").withDescription("example-description").withSlug("example-slug").build();

        when(productQueryFacade.getById(anyLong())).thenReturn(product);
        when(productService.generateSlug(anyString())).thenReturn("new-name");
        when(productQueryFacade.checkWhetherNameInResourceIsNotUsedByLoggedUser(anyString())).thenReturn(true);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productFacade.edit(productEditDTO);

        assertThat(result.getName()).isEqualTo(productEditDTO.name());
        assertThat(result.getSlug()).isEqualTo("new-name");
        assertThat(result.getDescription()).isEqualTo(productEditDTO.description());
        assertThat(result.getComposition()).isEqualTo(productEditDTO.composition());
    }

    @Test
    void should_return_bad_request_exception_from_edit_product() {
        ProductEditDTO productEditDTO = new ProductEditDTO(1L, "name", "description", "composition");
        Product product = Product.ProductBuilder.aProduct().withSlug(productEditDTO.name()).build();

        when(productQueryFacade.getById(anyLong())).thenReturn(product);
        when(productQueryFacade.checkWhetherNameInResourceIsNotUsedByLoggedUser(anyString())).thenReturn(false);

        assertThatThrownBy(() -> productFacade.edit(productEditDTO)).isInstanceOf(BadRequestException.class);
    }

    @Test
    void should_create_new_product_return_exception() {
        ProductCreateDTO productCreateDTO = new ProductCreateDTO("exampleName", "exampleDescription", "exampleComposition");

        when(productQueryFacade.checkWhetherNameInResourceIsNotUsedByLoggedUser(anyString())).thenReturn(false);

        assertThatThrownBy(() -> productFacade.create(productCreateDTO)).isInstanceOf(BadRequestException.class);
    }

    @Test
    void should_delete_product_by_id() {
        Product product = Product.ProductBuilder.aProduct().build();

        when(productQueryFacade.getById(anyLong())).thenReturn(product);

        productFacade.deleteById(anyLong());

        verify(productRepository, times(1)).delete(any());
    }
}