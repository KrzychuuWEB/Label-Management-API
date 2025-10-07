package pl.krzychuuweb.labelapp.nutritionalvalue;

import java.util.List;

public interface NutritionalValueQueryFacade {

    NutritionalValue getById(final Long id);

    List<NutritionalValue> getAll();
}
