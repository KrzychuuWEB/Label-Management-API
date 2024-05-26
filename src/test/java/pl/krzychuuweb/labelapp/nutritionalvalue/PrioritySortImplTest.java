package pl.krzychuuweb.labelapp.nutritionalvalue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PrioritySortImplTest {

    @InjectMocks
    private PrioritySortImpl prioritySort;

    @Test
    void should_get_parent_priority_from_list() {
        List<NutritionalValue> nutritionalValueList = List.of(
                NutritionalValueTestBuilder.aNutritionalValue().withPriority(new BigDecimal("1")).build(),
                NutritionalValueTestBuilder.aNutritionalValue().withPriority(new BigDecimal("3")).build(),
                NutritionalValueTestBuilder.aNutritionalValue().withPriority(new BigDecimal("2")).build(),
                NutritionalValueTestBuilder.aNutritionalValue().withPriority(new BigDecimal("3.3")).build()
        );

        List<NutritionalValue> result = prioritySort.getSortedParentPrioritiesFromList(nutritionalValueList);

        assertThat(result).hasSize(3);
        assertThat(result.get(0).getPriority()).isEqualTo(new BigDecimal("1"));
        assertThat(result.get(1).getPriority()).isEqualTo(new BigDecimal("2"));
        assertThat(result.get(2).getPriority()).isEqualTo(new BigDecimal("3"));
    }

    @Test
    void should_get_children_priority_from_list() {
        List<NutritionalValue> result = prioritySort.getSortedChildrenPrioritiesFromList(getNutritionalValueListWithFractionalPriorities());

        assertThat(result).hasSize(3);
        assertThat(result.get(0).getPriority()).isEqualTo(new BigDecimal("1.1"));
        assertThat(result.get(1).getPriority()).isEqualTo(new BigDecimal("2.1"));
        assertThat(result.get(2).getPriority()).isEqualTo(new BigDecimal("2.2"));
    }

    @Test
    void should_get_only_priorities_by_priority() {
        List<NutritionalValue> result = prioritySort.getSortedNutritionalValueListWithFractionalPrioritiesByIntegerPriority(getNutritionalValueListWithFractionalPriorities(), 2);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getPriority()).isEqualTo(new BigDecimal("2.1"));
        assertThat(result.get(1).getPriority()).isEqualTo(new BigDecimal("2.2"));
    }

    private List<NutritionalValue> getNutritionalValueListWithFractionalPriorities() {
        return List.of(
                NutritionalValueTestBuilder.aNutritionalValue().withPriority(new BigDecimal("1.1")).build(),
                NutritionalValueTestBuilder.aNutritionalValue().withPriority(new BigDecimal("2.2")).build(),
                NutritionalValueTestBuilder.aNutritionalValue().withPriority(new BigDecimal("2.1")).build(),
                NutritionalValueTestBuilder.aNutritionalValue().withPriority(new BigDecimal("3")).build()
        );
    }
}