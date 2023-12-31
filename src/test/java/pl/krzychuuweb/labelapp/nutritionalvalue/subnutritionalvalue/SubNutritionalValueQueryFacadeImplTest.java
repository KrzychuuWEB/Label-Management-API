package pl.krzychuuweb.labelapp.nutritionalvalue.subnutritionalvalue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.krzychuuweb.labelapp.exceptions.BadRequestException;
import pl.krzychuuweb.labelapp.exceptions.NotFoundException;

import java.util.List;
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

    @Test
    void should_get_all_sub_nutritional_values() {
        List<SubNutritionalValue> list = List.of(
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().build(),
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().build(),
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().build(),
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().build()
        );

        when(subNutritionalValueQueryRepository.findAll()).thenReturn(list);

        List<SubNutritionalValue> result = subNutritionalValueQueryFacade.getAll();

        assertThat(result).hasSameSizeAs(list);
    }

    @Test
    void should_priority_has_been_used_expected_exception() {
        when(subNutritionalValueQueryRepository.existsByPriority(anyInt())).thenReturn(true);

        assertThatThrownBy(() -> subNutritionalValueQueryFacade.checkWhetherPriorityIsNotUsed(anyInt())).isInstanceOf(BadRequestException.class);
    }

    @Test
    void should_get_all_by_priority_between_min_and_max() {
        List<SubNutritionalValue> nutritionalValueList = List.of(
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().withPriority(1).build(),
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().withPriority(2).build(),
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().withPriority(3).build()
        );

        when(subNutritionalValueQueryRepository.findAllByPriorityBetween(anyInt(), anyInt())).thenReturn(nutritionalValueList);

        List<SubNutritionalValue> result = subNutritionalValueQueryFacade.getAllByPriorityBetweenRange(anyInt(), anyInt());

        assertThat(result).hasSize(3);
    }
}