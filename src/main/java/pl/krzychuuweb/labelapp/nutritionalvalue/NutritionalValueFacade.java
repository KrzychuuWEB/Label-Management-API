package pl.krzychuuweb.labelapp.nutritionalvalue;

import pl.krzychuuweb.labelapp.nutritionalvalue.dto.NutritionalValueCreateDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.NutritionalValueEditDTO;

public interface NutritionalValueFacade {

    NutritionalValue create(final NutritionalValueCreateDTO nutritionalValueCreateDTO);

    NutritionalValue edit(final NutritionalValueEditDTO nutritionalValueEditDTO, final Long id);

    void deleteById(final Long id);
}
