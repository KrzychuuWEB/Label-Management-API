package pl.krzychuuweb.labelapp.nutritionalvalue.subnutritionalvalue;

import org.springframework.stereotype.Component;
import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValue;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.CreateNutritionalValueDTO;

@Component
class SubNutritionalValueFactory {

    SubNutritionalValue createSubNutritionalValue(CreateNutritionalValueDTO createNutritionalValueDTO, NutritionalValue nutritionalValue) {
        return SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue()
                .withName(createNutritionalValueDTO.name())
                .withPriority(createNutritionalValueDTO.priority())
                .withNutritionalValue(nutritionalValue)
                .build();
    }
}
