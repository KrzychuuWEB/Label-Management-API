package pl.krzychuuweb.labelapp.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.krzychuuweb.labelapp.auth.AuthQueryFacade;
import pl.krzychuuweb.labelapp.exception.NotFoundException;
import pl.krzychuuweb.labelapp.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductQueryFacadeImplTest {

    @Mock
    private ProductQueryRepository productQueryRepository;

    @Mock
    private AuthQueryFacade authQueryFacade;

    @InjectMocks
    private ProductQueryFacadeImpl productQueryFacade;

    @Test
    void should_name_is_not_used_by_user() {
        User user = User.UserBuilder.anUser().withEmail("email@email.com").build();

        when(authQueryFacade.getLoggedUser()).thenReturn(user);
        when(productQueryRepository.existsByNameAndUser(anyString(), any(User.class))).thenReturn(false);

        boolean result = productQueryFacade.checkWhetherNameInResourceIsNotUsedByLoggedUser("exampleName");

        assertTrue(result);
    }

    @Test
    void should_name_is_used_by_user() {
        User user = User.UserBuilder.anUser().withEmail("email@email.com").build();

        when(authQueryFacade.getLoggedUser()).thenReturn(user);
        when(productQueryRepository.existsByNameAndUser(anyString(), any(User.class))).thenReturn(true);

        boolean result = productQueryFacade.checkWhetherNameInResourceIsNotUsedByLoggedUser("exampleName");

        assertFalse(result);
    }

    @Test
    void should_slug_is_used_by_user() {
        User user = User.UserBuilder.anUser().withEmail("email@email.com").build();

        when(authQueryFacade.getLoggedUser()).thenReturn(user);
        when(productQueryRepository.existsBySlugAndUser(anyString(), any(User.class))).thenReturn(true);

        boolean result = productQueryFacade.checkWhetherSlugIsNotUsedByUser("exampleSlug");

        assertFalse(result);
    }


    @Test
    void should_slug_is_not_used_by_user() {
        User user = User.UserBuilder.anUser().withEmail("email@email.com").build();

        when(authQueryFacade.getLoggedUser()).thenReturn(user);
        when(productQueryRepository.existsBySlugAndUser(anyString(), any(User.class))).thenReturn(false);

        boolean result = productQueryFacade.checkWhetherSlugIsNotUsedByUser("exampleSlug");

        assertTrue(result);
    }

    @Test
    void should_return_product_by_id() {
        User user = User.UserBuilder.anUser().withEmail("eamil@email.com").build();
        Product product = Product.ProductBuilder.aProduct()
                .withId(1L)
                .withName("exampleName")
                .withUser(user)
                .withComposition("exampleComposition")
                .build();

        when(productQueryRepository.findById(anyLong())).thenReturn(Optional.of(product));

        Product result = productQueryFacade.getById(anyLong());

        assertThat(result).isInstanceOf(Product.class);
        assertThat(result.getId()).isEqualTo(product.getId());
        assertThat(result.getName()).isEqualTo(product.getName());
        assertThat(result.getComposition()).isEqualTo(product.getComposition());
        assertThat(result.getUser()).isInstanceOf(User.class);
    }

    @Test
    void should_get_product_by_id_return_not_found_exception() {
        when(productQueryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productQueryFacade.getById(anyLong())).isInstanceOf(NotFoundException.class);
    }

    @Test
    void should_get_product_by_slug_and_auth_user() {
        User user = User.UserBuilder.anUser().withEmail("email@email.com").build();
        Product product = Product.ProductBuilder.aProduct().withSlug("example-slug").withUser(user).build();

        when(authQueryFacade.getLoggedUser()).thenReturn(user);
        when(productQueryRepository.getBySlugAndUser(anyString(), any(User.class))).thenReturn(Optional.of(product));

        Product result = productQueryFacade.getProductBySlugAndAuthUser("example-slug");

        assertThat(result.getSlug()).isEqualTo(product.getSlug());
        assertThat(result.getUser()).isInstanceOf(User.class);
        assertThat(result.getUser().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void should_get_product_by_slug_and_auth_user_return_exception() {
        User user = User.UserBuilder.anUser().withEmail("email@email.com").build();

        when(authQueryFacade.getLoggedUser()).thenReturn(user);
        when(productQueryRepository.getBySlugAndUser(anyString(), any(User.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productQueryFacade.getProductBySlugAndAuthUser("example-slug")).isInstanceOf(NotFoundException.class);
    }

    @Test
    void should_get_all_products_by_auth_user() {
        User user = User.UserBuilder.anUser().withEmail("email@email.com").build();
        List<Product> products = new ArrayList<>(List.of(
                Product.ProductBuilder.aProduct().build(),
                Product.ProductBuilder.aProduct().build(),
                Product.ProductBuilder.aProduct().build(),
                Product.ProductBuilder.aProduct().build()
        ));

        when(authQueryFacade.getLoggedUser()).thenReturn(user);
        when(productQueryRepository.getAllByUser(any(User.class))).thenReturn(products);

        List<Product> result = productQueryFacade.getAllProductByAuthUser();

        assertThat(result).hasSameSizeAs(products);
    }
}