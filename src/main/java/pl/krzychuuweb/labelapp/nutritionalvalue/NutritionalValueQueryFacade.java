package pl.krzychuuweb.labelapp.nutritionalvalue;

import java.util.List;

public interface NutritionalValueQueryFacade {

    List<NutritionalValue> getAll();

    NutritionalValue getById(final Long id);

    boolean checkWhetherPriorityIsNotUsed(final Integer priority);
}
