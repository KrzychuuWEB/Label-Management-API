package pl.krzychuuweb.labelapp.nutritionalvalue;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Service
class PrioritySortImpl implements PrioritySort {

    @Override
    public List<NutritionalValue> getSortedParentPrioritiesFromList(final List<NutritionalValue> list) {
        return list.stream()
                .filter(item -> item.getPriority().compareTo(BigDecimal.ZERO) >= 0 && item.getPriority().remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0)
                .sorted(Comparator.comparing(NutritionalValue::getPriority))
                .toList();
    }

    @Override
    public List<NutritionalValue> getSortedChildrenPrioritiesFromList(final List<NutritionalValue> list) {
        return list.stream()
                .filter(item -> item.getPriority().remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) != 0)
                .sorted(Comparator.comparing(NutritionalValue::getPriority))
                .toList();
    }

    @Override
    public List<NutritionalValue> getSortedNutritionalValueListWithFractionalPrioritiesByIntegerPriority(final List<NutritionalValue> list, final int priority) {
        return list.stream()
                .filter(item -> item.getPriority().intValue() == priority)
                .sorted(Comparator.comparing(NutritionalValue::getPriority))
                .toList();
    }
}
