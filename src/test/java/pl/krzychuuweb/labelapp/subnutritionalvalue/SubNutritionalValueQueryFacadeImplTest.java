package pl.krzychuuweb.labelapp.subnutritionalvalue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.krzychuuweb.labelapp.exceptions.BadRequestException;
import pl.krzychuuweb.labelapp.exceptions.NotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SubNutritionalValueQueryFacadeImplTest {

    @Autowired
    private SubNutritionalValueQueryRepository subNutritionalValueQueryRepository;

    private SubNutritionalValueQueryFacade subNutritionalValueQueryFacade;

    @BeforeEach
    void setup() {
        subNutritionalValueQueryRepository = mock(SubNutritionalValueQueryRepository.class);
        subNutritionalValueQueryFacade = new SubNutritionalValueQueryFacadeImpl(subNutritionalValueQueryRepository);
    }

    @Test
    void should_priority_has_been_used_expected_exception() {
        when(subNutritionalValueQueryRepository.existsByPriority(anyInt())).thenReturn(true);

        assertThatThrownBy(() -> subNutritionalValueQueryFacade.checkWhetherPriorityIsNotUsed(anyInt())).isInstanceOf(BadRequestException.class);
    }

    @Test
    void should_priority_has_been_not_used() {
        when(subNutritionalValueQueryRepository.existsByPriority(anyInt())).thenReturn(false);

        boolean result = subNutritionalValueQueryFacade.checkWhetherPriorityIsNotUsed(anyInt());

        assertThat(result).isTrue();
    }

    @Test
    void should_get_sub_nutritional_value_by_id() {
        SubNutritionalValue subNutritionalValue = SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue()
                .withName("exampleName")
                .withPriority(1)
                .build();

        when(subNutritionalValueQueryRepository.findById(anyLong())).thenReturn(Optional.of(subNutritionalValue));

        SubNutritionalValue result = subNutritionalValueQueryFacade.getById(anyLong());

        assertThat(result.getName()).isEqualTo(subNutritionalValue.getName());
        assertThat(result.getPriority()).isEqualTo(subNutritionalValue.getPriority());
    }

    @Test
    void should_get_sub_nutritional_value_by_id_throw_not_found_exception() {
        when(subNutritionalValueQueryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> subNutritionalValueQueryFacade.getById(anyLong())).isInstanceOf(NotFoundException.class);
    }
}