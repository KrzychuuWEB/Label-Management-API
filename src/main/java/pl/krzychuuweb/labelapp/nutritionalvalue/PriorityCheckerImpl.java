package pl.krzychuuweb.labelapp.nutritionalvalue;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
class PriorityCheckerImpl implements PriorityChecker {

    private final PrioritySort prioritySort;

    PriorityCheckerImpl(final PrioritySort prioritySort) {
        this.prioritySort = prioritySort;
    }

    @Override
    public List<NutritionalValue> checkParentPriorities(final List<NutritionalValue> parentPrioritiesList) {
        List<NutritionalValue> sortedList = prioritySort.getSortedParentPrioritiesFromList(parentPrioritiesList);

        for (int i = 0; i < sortedList.size(); i++) {
            if (sortedList.get(i).getPriority().compareTo(new BigDecimal(i + 1)) != 0) {
                return List.of();
            }
        }

        return sortedList;
    }

    @Override
    public List<NutritionalValue> checkChildrenPriorities(final List<NutritionalValue> childrenPrioritiesList) {
        List<NutritionalValue> sortedList = prioritySort.getSortedChildrenPrioritiesFromList(childrenPrioritiesList);

        for (int i : getIntegerPrioritiesFromFractionalPrioritiesList(sortedList)) {
            List<NutritionalValue> listToCheck = prioritySort.getSortedNutritionalValueListWithFractionalPrioritiesByIntegerPriority(sortedList, i);

            for (int y = 0; y < listToCheck.size(); y++) {
                if (listToCheck.get(y).getPriority().compareTo(changeIntegerToBigDecimalWithFractional(i, y).add(new BigDecimal("0.1"))) != 0) {
                    return List.of();
                }
            }
        }

        return childrenPrioritiesList;
    }

    private Set<Integer> getIntegerPrioritiesFromFractionalPrioritiesList(final List<NutritionalValue> list) {
        return list.stream()
                .map(item -> item.getPriority().intValue())
                .collect(Collectors.toSet());
    }

    private BigDecimal changeIntegerToBigDecimalWithFractional(final int integer, final int fractional) {
        return new BigDecimal(integer).add(new BigDecimal(fractional).divide(new BigDecimal(10)));
    }
}
