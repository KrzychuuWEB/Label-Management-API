package pl.krzychuuweb.labelapp.nutritionalvalue;

import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.exception.InvalidPriorityException;

import java.math.BigDecimal;
import java.util.List;

@Service
class PrioritySettingImpl implements PrioritySetting {

    private final PriorityChecker priorityChecker;

    private final PrioritySort prioritySort;

    PrioritySettingImpl(final PriorityChecker priorityChecker, final PrioritySort prioritySort) {
        this.priorityChecker = priorityChecker;
        this.prioritySort = prioritySort;
    }

    @Override
    public List<NutritionalValue> setNewParentPriorities(final List<NutritionalValue> nutritionalValueList) {
        List<NutritionalValue> sortedList = prioritySort.getSortedParentPrioritiesFromList(nutritionalValueList);

        for (int i = 0; i < sortedList.size(); i++) {
            if (sortedList.get(i).getPriority().equals(new BigDecimal(i + 1))) {
                sortedList.get(i).setPriority(new BigDecimal(i + 1));
            }
        }

        if (priorityChecker.checkParentPriorities(nutritionalValueList).isEmpty()) {
            throw new InvalidPriorityException("Priorities is bad set");
        }

        return sortedList;
    }
}