package pl.krzychuuweb.labelapp.nutritionalvalue.subnutritionalvalue;

import pl.krzychuuweb.labelapp.nutritionalvalue.PriorityQueryFacade;

import java.util.List;

public interface SubNutritionalValueQueryFacade extends PriorityQueryFacade<SubNutritionalValue> {

    List<SubNutritionalValue> getAllByListId(final List<Long> idList);
}
