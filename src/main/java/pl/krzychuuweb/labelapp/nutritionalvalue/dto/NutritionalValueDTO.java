package pl.krzychuuweb.labelapp.nutritionalvalue.dto;

import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValue;

import java.util.List;

public record NutritionalValueDTO(
        Long id,
        String name,
        Float priority
) {
    public static List<NutritionalValueDTO> mapNutritionalValueListToNutritionalValueDTO(List<NutritionalValue> nutritionalValues) {
        return nutritionalValues.stream()
                .map(nutritionalValue ->
                        new NutritionalValueDTO(
                                nutritionalValue.getId(),
                                nutritionalValue.getName(),
                                nutritionalValue.getPriority()
                        )).toList();
    }

    public static NutritionalValueDTO mapNutritionalValueToNutritionalValueDTO(final NutritionalValue nutritionalValue) {
        return new NutritionalValueDTO(
                nutritionalValue.getId(),
                nutritionalValue.getName(),
                nutritionalValue.getPriority()
        );
    }
}
