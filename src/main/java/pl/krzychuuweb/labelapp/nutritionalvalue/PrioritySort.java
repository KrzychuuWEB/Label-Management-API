package pl.krzychuuweb.labelapp.nutritionalvalue;

import java.util.List;

public interface PrioritySort {

    List<NutritionalValue> getSortedParentPrioritiesFromList(final List<NutritionalValue> list);

    List<NutritionalValue> getSortedChildrenPrioritiesFromList(final List<NutritionalValue> list);

    List<NutritionalValue> getSortedNutritionalValueListWithFractionalPrioritiesByIntegerPriority(final List<NutritionalValue> list, final int priority);
}
