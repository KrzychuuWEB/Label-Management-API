package pl.krzychuuweb.labelapp.batchnutritionalvalues.dto;


import pl.krzychuuweb.labelapp.nutritionalvalue.dto.NutritionalValueUseDTO;
import pl.krzychuuweb.labelapp.validator.nutritionalvalue.CheckSubNutritionalValueGroup;

import java.util.List;

@CheckSubNutritionalValueGroup
public record BatchNutritionalValuesCreateDTO(

        List<NutritionalValueUseDTO> nutritionalValueIdList,

        List<NutritionalValueUseDTO> subNutritionalValueIdList
) {
}
