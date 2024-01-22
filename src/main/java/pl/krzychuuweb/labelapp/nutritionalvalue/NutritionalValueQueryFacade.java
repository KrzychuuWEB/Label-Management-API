package pl.krzychuuweb.labelapp.nutritionalvalue;

import java.util.List;

public interface NutritionalValueQueryFacade extends PriorityQueryFacade<NutritionalValue> {

    List<NutritionalValue> getAllByListId(final List<Long> idList);
}
