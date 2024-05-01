package pl.krzychuuweb.labelapp.batchnutritionalmapping;

import org.springframework.stereotype.Component;
import pl.krzychuuweb.labelapp.batch.Batch;
import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValue;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.NutritionalValueUseDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.subnutritionalvalue.SubNutritionalValue;

import java.util.ArrayList;
import java.util.List;

@Component
class BatchNutritionalMappingFactory {

    List<BatchNutritionalMapping> createBatchNutritional(
            final List<? extends BatchNutritionalMappingStrategy> nutritional,
            final List<NutritionalValueUseDTO> nutritionalValueUseDTO,
            final Batch batch
    ) {
        List<BatchNutritionalMapping> batchNutritionalMappingList = new ArrayList<>();

        nutritional.stream()
                .map(nutritionalValue -> {
                    String value = nutritionalValueUseDTO.stream()
                            .filter(dto -> dto.id().equals(nutritionalValue.getId()))
                            .map(NutritionalValueUseDTO::value)
                            .findFirst()
                            .orElse("Error");

                    if (nutritionalValue instanceof NutritionalValue) {
                        return BatchNutritionalMapping.BatchNutritionalMappingBuilder.aBatchNutritionalMapping()
                                .withBatch(batch)
                                .withNutritionalValue((NutritionalValue) nutritionalValue)
                                .withValue(value)
                                .build();
                    } else {
                        return BatchNutritionalMapping.BatchNutritionalMappingBuilder.aBatchNutritionalMapping()
                                .withBatch(batch)
                                .withSubNutritionalValue((SubNutritionalValue) nutritionalValue)
                                .withValue(value)
                                .build();
                    }

                })
                .forEach(batchNutritionalMappingList::add);

        return batchNutritionalMappingList;
    }
}
