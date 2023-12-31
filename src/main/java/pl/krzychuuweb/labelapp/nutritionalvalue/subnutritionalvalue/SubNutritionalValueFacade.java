package pl.krzychuuweb.labelapp.nutritionalvalue.subnutritionalvalue;

import pl.krzychuuweb.labelapp.nutritionalvalue.PriorityFacade;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.CreateNutritionalValueDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.EditNutritionalValueDTO;

public interface SubNutritionalValueFacade extends PriorityFacade<SubNutritionalValue> {

    SubNutritionalValue add(final CreateNutritionalValueDTO createNutritionalValueDTO, Long nutritionalId);

    SubNutritionalValue edit(final EditNutritionalValueDTO editNutritionalValueDTO);

    void delete(final Long id);
}
