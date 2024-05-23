package pl.krzychuuweb.labelapp.nutritionalvalue;

import java.util.List;

interface PriorityChecker {
    
    List<NutritionalValue> checkParentPriorities(final List<NutritionalValue> parentPrioritiesList);

    List<NutritionalValue> checkChildrenPriorities(final List<NutritionalValue> childrenPrioritiesList);
}
