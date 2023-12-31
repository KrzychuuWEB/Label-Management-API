package pl.krzychuuweb.labelapp.nutritionalvalue;

import pl.krzychuuweb.labelapp.nutritionalvalue.dto.ChangeNutritionalValuePriorityDTO;

import java.util.List;

public interface PriorityFacade<T extends Priority> {

    List<T> editPriority(final ChangeNutritionalValuePriorityDTO changeNutritionalValuePriorityDTO);
}
