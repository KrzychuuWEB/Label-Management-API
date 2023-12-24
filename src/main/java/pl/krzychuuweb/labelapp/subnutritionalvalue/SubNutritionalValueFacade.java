package pl.krzychuuweb.labelapp.subnutritionalvalue;

import pl.krzychuuweb.labelapp.nutritionalvalue.dto.CreateNutritionalValueDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.EditNutritionalValueDTO;

public interface SubNutritionalValueFacade {

    SubNutritionalValue add(final CreateNutritionalValueDTO createNutritionalValueDTO, Long nutritionalId);

    SubNutritionalValue edit(final EditNutritionalValueDTO editNutritionalValueDTO);

    void delete(final Long id);
}
