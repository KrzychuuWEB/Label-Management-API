package pl.krzychuuweb.labelapp.nutritionalvalue;

import org.springframework.stereotype.Component;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.CreateNutritionalValueDTO;

@Component
class NutritionalValueFactory {

    NutritionalValue createNutritionalValue(CreateNutritionalValueDTO createNutritionalValueDTO) {
        return NutritionalValue.NutritionalValueBuilder.aNutritionalValue()
                .withName(createNutritionalValueDTO.name())
                .withPriority(createNutritionalValueDTO.priority())
                .build();
    }
}
