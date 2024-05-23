package pl.krzychuuweb.labelapp.nutritionalvalue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.krzychuuweb.labelapp.exception.BadRequestException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NutritionalValueQueryFacadeImplTest {

    @Mock
    private NutritionalValueQueryRepository nutritionalValueQueryRepository;

    @InjectMocks
    private NutritionalValueQueryFacadeImpl nutritionalValueQueryFacade;

    @Test
    void should_get_nutritional_value_by_id() {
        NutritionalValue nutritionalValue = NutritionalValueTestBuilder.aNutritionalValue().build();

        when(nutritionalValueQueryRepository.findById(anyLong())).thenReturn(Optional.of(nutritionalValue));

        NutritionalValue result = nutritionalValueQueryFacade.getById(anyLong());

        assertThat(result.getId()).isEqualTo(nutritionalValue.getId());
        assertThat(result.getName()).isEqualTo(nutritionalValue.getName());
        assertThat(result.getPriority()).isEqualTo(nutritionalValue.getPriority());
    }

    @Test
    void should_return_exception_for_nutritional_get_by_id_because_nutritional_value_not_exists() {
        when(nutritionalValueQueryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> nutritionalValueQueryFacade.getById(anyLong())).isInstanceOf(BadRequestException.class);
    }

    @Test
    void should_get_all_nutritional_values() {
        List<NutritionalValue> list = List.of(
                NutritionalValueTestBuilder.aNutritionalValue().build(),
                NutritionalValueTestBuilder.aNutritionalValue().build(),
                NutritionalValueTestBuilder.aNutritionalValue().build(),
                NutritionalValueTestBuilder.aNutritionalValue().build()
        );

        when(nutritionalValueQueryRepository.findAll()).thenReturn(list);

        List<NutritionalValue> result = nutritionalValueQueryFacade.getAll();

        assertThat(result).hasSameSizeAs(list);
    }
}