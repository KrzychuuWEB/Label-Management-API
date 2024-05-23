package pl.krzychuuweb.labelapp.nutritionalvalue;

import org.springframework.stereotype.Component;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.NutritionalValueCreateDTO;

import java.math.BigDecimal;

@Component
class NutritionalValueFactory {

    NutritionalValue createNutritionalValue(
            final NutritionalValueCreateDTO nutritionalValueCreateDTO,
            final BigDecimal priority
    ) {
        return NutritionalValue.NutritionalValueBuilder.aNutritionalValue()
                .withName(nutritionalValueCreateDTO.name())
                .withPriority(priority)
                .build();
    }
}
