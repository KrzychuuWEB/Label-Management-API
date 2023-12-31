package pl.krzychuuweb.labelapp.nutritionalvalue.dto;

import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValue;
import pl.krzychuuweb.labelapp.nutritionalvalue.subnutritionalvalue.dto.SubNutritionalValueDTO;

import java.time.LocalDateTime;
import java.util.List;

public record NutritionalValueDTO(
        Long id,
        String name,
        Integer priority,
        List<SubNutritionalValueDTO> subNutritionalValueDTO,
        LocalDateTime createdAt
) {
    public static List<NutritionalValueDTO> mapNutritionalValueListToNutritionalValueDTO(List<NutritionalValue> nutritionalValues) {
        return nutritionalValues.stream()
                .map(nutritionalValue ->
                        new NutritionalValueDTO(
                                nutritionalValue.getId(),
                                nutritionalValue.getName(),
                                nutritionalValue.getPriority(),
                                SubNutritionalValueDTO.mapToSubNutritionalValueList(nutritionalValue.getSubNutritionalValues()),
                                nutritionalValue.getCreatedAt()
                        )).toList();
    }

    public static NutritionalValueDTO mapNutritionalValueToNutritionalValueDTO(final NutritionalValue nutritionalValue) {
        return new NutritionalValueDTO(
                nutritionalValue.getId(),
                nutritionalValue.getName(),
                nutritionalValue.getPriority(),
                SubNutritionalValueDTO.mapToSubNutritionalValueList(nutritionalValue.getSubNutritionalValues()),
                nutritionalValue.getCreatedAt()
        );
    }
}
