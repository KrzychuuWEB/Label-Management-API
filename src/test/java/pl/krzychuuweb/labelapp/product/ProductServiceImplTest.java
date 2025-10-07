package pl.krzychuuweb.labelapp.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductQueryFacade productQueryFacade;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void should_generate_slug_from_string_return_good_string() {
        String name = "ĄĆĘŁŃÓŚŻŹ ABC ąćęłńóśżź";

        when(productQueryFacade.checkWhetherSlugIsNotUsedByUser(anyString())).thenReturn(true);

        String result = productService.generateSlug(name);

        assertThat(result).isEqualTo("acelnoszz-abc-acelnoszz");
    }

    @Test
    void should_generate_slug_from_string_return_good_string_with_random_integer_because_slug_is_taken() {
        String name = "name taken";

        when(productQueryFacade.checkWhetherSlugIsNotUsedByUser(anyString())).thenReturn(false).thenReturn(true);

        String result = productService.generateSlug(name);

        assertThat(result.substring(0, result.length() - 4)).isEqualTo("name-taken");
        assertTrue(result.matches(".*\\d{4}$"));
    }

    @Test
    void should_generate_slug_from_string_return_exception() {
        String name = "ĄĆ<?>%^%#$^$#";

        assertThatThrownBy(() -> productService.generateSlug(name)).isInstanceOf(IllegalArgumentException.class);
    }
}