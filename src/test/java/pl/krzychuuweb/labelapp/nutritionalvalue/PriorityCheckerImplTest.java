package pl.krzychuuweb.labelapp.nutritionalvalue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriorityCheckerImplTest {

    @Mock
    private PrioritySort prioritySort;

    @InjectMocks
    private PriorityCheckerImpl priorityChecker;

    @Test
    void should_return_checked_parent_priorities_list_with_good_priorities_and_sorted() {
        List<NutritionalValue> badSortedList = List.of(
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(new BigDecimal("2")).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(new BigDecimal("4")).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(new BigDecimal("3")).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(new BigDecimal("1")).build()
        );

        List<NutritionalValue> goodSortedList = List.of(
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(new BigDecimal("1")).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(new BigDecimal("2")).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(new BigDecimal("3")).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(new BigDecimal("4")).build()
        );

        when(prioritySort.getSortedParentPrioritiesFromList(anyList())).thenReturn(goodSortedList);

        List<NutritionalValue> result = priorityChecker.checkParentPriorities(badSortedList);

        List<BigDecimal> priorities = result.stream().map(NutritionalValue::getPriority).toList();

        assertThat(result).hasSameSizeAs(badSortedList);
        assertThat(priorities).isSorted();
    }

    @Test
    void should_return_empty_array_for_check_parent_priorities_by_bad_priorities() {
        List<NutritionalValue> badPrioritiesList = List.of(
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(new BigDecimal("1")).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(new BigDecimal("5")).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(new BigDecimal("3")).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(new BigDecimal("4")).build()
        );

        List<NutritionalValue> goodSortedList = List.of(
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(new BigDecimal("1")).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(new BigDecimal("3")).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(new BigDecimal("4")).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(new BigDecimal("5")).build()
        );

        when(prioritySort.getSortedParentPrioritiesFromList(anyList())).thenReturn(goodSortedList);

        List<NutritionalValue> result = priorityChecker.checkParentPriorities(badPrioritiesList);

        assertThat(result).isEmpty();
    }

    @Test
    void should_return_checked_fractional_priorities_list_with_sorted() {
        List<NutritionalValue> nutritionalValueList = List.of(
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(new BigDecimal("1.1")).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(new BigDecimal("1.2")).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(new BigDecimal("2.1")).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(new BigDecimal("3.1")).build()
        );

        when(prioritySort.getSortedChildrenPrioritiesFromList(anyList())).thenReturn(nutritionalValueList);

        List<NutritionalValue> result = priorityChecker.checkChildrenPriorities(nutritionalValueList);

        List<BigDecimal> priorities = result.stream().map(NutritionalValue::getPriority).toList();

        assertThat(result).hasSameSizeAs(nutritionalValueList);
        assertThat(priorities).isSorted();
    }

    @Test
    void should_return_empty_array_for_check_children_priorities_by_bad_priorities() {
        List<NutritionalValue> nutritionalValueList = List.of(
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(new BigDecimal("1.1")).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(new BigDecimal("1.2")).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(new BigDecimal("2.2")).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(new BigDecimal("3.1")).build()
        );

        List<NutritionalValue> listForPriority1 = List.of(
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(new BigDecimal("1.1")).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(new BigDecimal("1.2")).build()
        );

        List<NutritionalValue> listForPriority2 = List.of(
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(new BigDecimal("2.1")).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(new BigDecimal("2.3")).build()
        );

        when(prioritySort.getSortedChildrenPrioritiesFromList(anyList())).thenReturn(nutritionalValueList);
        when(prioritySort.getSortedNutritionalValueListWithFractionalPrioritiesByIntegerPriority(nutritionalValueList, 1))
                .thenReturn(listForPriority1);
        when(prioritySort.getSortedNutritionalValueListWithFractionalPrioritiesByIntegerPriority(nutritionalValueList, 2))
                .thenReturn(listForPriority2);

        List<NutritionalValue> result = priorityChecker.checkChildrenPriorities(nutritionalValueList);

        assertThat(result).isEmpty();
    }
}