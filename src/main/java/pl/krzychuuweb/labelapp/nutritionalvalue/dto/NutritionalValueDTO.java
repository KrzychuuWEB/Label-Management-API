package pl.krzychuuweb.labelapp.nutritionalvalue.dto;

import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValue;

import java.time.LocalDateTime;
import java.util.List;

public record NutritionalValueDTO(
        Long id,
        String name,
        Float priority,

        LocalDateTime createdAt
) {
    public static List<NutritionalValueDTO> mapNutritionalValueListToNutritionalValueDTO(List<NutritionalValue> nutritionalValues) {
        return nutritionalValues.stream()
                .map(nutritionalValue ->
                        new NutritionalValueDTO(
                                nutritionalValue.getId(),
                                nutritionalValue.getName(),
                                nutritionalValue.getPriority(),
                                nutritionalValue.getCreatedAt()
                        )).toList();
    }

    public static NutritionalValueDTO mapNutritionalValueToNutritionalValueDTO(final NutritionalValue nutritionalValue) {
        return new NutritionalValueDTO(
                nutritionalValue.getId(),
                nutritionalValue.getName(),
                nutritionalValue.getPriority(),
                nutritionalValue.getCreatedAt()
        );
    }
}
