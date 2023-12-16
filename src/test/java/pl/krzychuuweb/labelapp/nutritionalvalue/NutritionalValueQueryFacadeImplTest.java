package pl.krzychuuweb.labelapp.nutritionalvalue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.krzychuuweb.labelapp.exceptions.BadRequestException;
import pl.krzychuuweb.labelapp.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NutritionalValueQueryFacadeImplTest {

    @Autowired
    private NutritionalValueQueryRepository nutritionalValueQueryRepository;

    private NutritionalValueQueryFacadeImpl nutritionalValueQueryFacade;

    @BeforeEach
    void setUp() {
        nutritionalValueQueryRepository = mock(NutritionalValueQueryRepository.class);
        nutritionalValueQueryFacade = new NutritionalValueQueryFacadeImpl(nutritionalValueQueryRepository);
    }

    @Test
    void should_get_all_nutritional_values() {
        List<NutritionalValue> nutritionalValues = new ArrayList<>();
        nutritionalValues.add(NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withName("Example1").withPriority(1F).build());
        nutritionalValues.add(NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withName("Example2").withPriority(2F).build());
        nutritionalValues.add(NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withName("Example3").withPriority(3F).build());

        when(nutritionalValueQueryRepository.findAll()).thenReturn(nutritionalValues);

        List<NutritionalValue> result = nutritionalValueQueryFacade.getAll();

        assertThat(result).hasSize(nutritionalValues.size());
    }

    @Test
    void should_get_nutritional_value_by_id() {
        NutritionalValue nutritionalValue = NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withName("Example1").withPriority(1F).build();

        when(nutritionalValueQueryRepository.findById(anyLong())).thenReturn(Optional.of(nutritionalValue));

        NutritionalValue result = nutritionalValueQueryFacade.getById(anyLong());

        assertThat(result.getId()).isEqualTo(nutritionalValue.getId());
        assertThat(result.getName()).isEqualTo(nutritionalValue.getName());
        assertThat(result.getPriority()).isEqualTo(nutritionalValue.getPriority());
    }

    @Test
    void should_get_nutritional_value_by_id_expect_exception() {
        when(nutritionalValueQueryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> nutritionalValueQueryFacade.getById(anyLong())).isInstanceOf(NotFoundException.class);
    }

    @Test
    void should_priority_has_been_used() {
        when(nutritionalValueQueryRepository.existsByPriority(anyFloat())).thenReturn(true);

        assertThatThrownBy(() -> nutritionalValueQueryFacade.checkWhetherPriorityIsNotUsed(anyFloat())).isInstanceOf(BadRequestException.class);
    }

    @Test
    void should_priority_is_not_used() {
        when(nutritionalValueQueryRepository.existsByPriority(anyFloat())).thenReturn(false);

        boolean result = nutritionalValueQueryFacade.checkWhetherPriorityIsNotUsed(anyFloat());

        assertThat(result).isTrue();
    }
}