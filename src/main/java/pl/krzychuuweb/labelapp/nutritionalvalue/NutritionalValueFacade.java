package pl.krzychuuweb.labelapp.nutritionalvalue;

import pl.krzychuuweb.labelapp.nutritionalvalue.dto.CreateNutritionalValueDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.EditNutritionalValueDTO;

public interface NutritionalValueFacade extends PriorityFacade<NutritionalValue> {

    NutritionalValue add(final CreateNutritionalValueDTO createNutritionalValueDTO);

    void delete(Long id);

    NutritionalValue edit(final EditNutritionalValueDTO editNutritionalValueDTO);
}
