package pl.krzychuuweb.labelapp.subnutritionalvalue.dto;

import pl.krzychuuweb.labelapp.subnutritionalvalue.SubNutritionalValue;

import java.util.List;
import java.util.stream.Stream;

public record SubNutritionalValueDTO(
        Long id,
        String name,
        Integer priority
) {
    public static List<SubNutritionalValueDTO> mapToSubNutritionalValueList(List<SubNutritionalValue> subNutritionalValueList) {
        return Stream.ofNullable(subNutritionalValueList)
                .flatMap(List::stream)
                .map(subNutritionalValue ->
                        new SubNutritionalValueDTO(
                                subNutritionalValue.getId(),
                                subNutritionalValue.getName(),
                                subNutritionalValue.getPriority()
                        )
                )
                .toList();
    }

    public static SubNutritionalValueDTO mapSubNutritionalValueToDTO(SubNutritionalValue subNutritionalValue) {
        return new SubNutritionalValueDTO(
                subNutritionalValue.getId(),
                subNutritionalValue.getName(),
                subNutritionalValue.getPriority()
        );
    }
}
