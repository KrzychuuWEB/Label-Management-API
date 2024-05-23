//package pl.krzychuuweb.labelapp.batchnutritionalmapping;
//
//import org.springframework.stereotype.Component;
//import pl.krzychuuweb.labelapp.batch.Batch;
//import pl.krzychuuweb.labelapp.exception.BadRequestException;
//import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValue;
//import pl.krzychuuweb.labelapp.nutritionalvalue.dto.NutritionalValueUseDTO;
//import pl.krzychuuweb.labelapp.nutritionalvalue.subnutritionalvalue.SubNutritionalValue;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@Component
//class BatchNutritionalMappingFactory {
//
//    List<BatchNutritionalMapping> createBatchNutritional(
//            final List<? extends BatchNutritionalMappingStrategy> nutritionalList,
//            final List<NutritionalValueUseDTO> nutritionalValueUseDTO,
//            final Batch batch
//    ) {
//        List<BatchNutritionalMapping> batchNutritionalMappingList = new ArrayList<>();
//
//        checkIfListsIsHasSameSize(nutritionalValueUseDTO, nutritionalList);
//
//        checkIfNutritionalValuesExists(nutritionalValueUseDTO, nutritionalList);
//
//        nutritionalList.stream()
//                .map(nutritionalValue -> {
//                    if (nutritionalValue instanceof NutritionalValue) {
//                        return BatchNutritionalMapping.BatchNutritionalMappingBuilder.aBatchNutritionalMapping()
//                                .withBatch(batch)
//                                .withNutritionalValue((NutritionalValue) nutritionalValue)
//                                .withValue(getNutritionalValueFromDTOByNutritionalId(nutritionalValueUseDTO, nutritionalValue.getId()))
//                                .build();
//                    } else {
//                        return BatchNutritionalMapping.BatchNutritionalMappingBuilder.aBatchNutritionalMapping()
//                                .withBatch(batch)
//                                .withSubNutritionalValue((SubNutritionalValue) nutritionalValue)
//                                .withValue(getNutritionalValueFromDTOByNutritionalId(nutritionalValueUseDTO, nutritionalValue.getId()))
//                                .build();
//                    }
//                })
//                .forEach(batchNutritionalMappingList::add);
//
//        return batchNutritionalMappingList;
//    }
//
//    private String getNutritionalValueFromDTOByNutritionalId(final List<NutritionalValueUseDTO> dtoList, final Long nutritionalId) {
//        return dtoList.stream()
//                .filter(dto -> dto.id().equals(nutritionalId))
//                .map(NutritionalValueUseDTO::value)
//                .findFirst()
//                .orElse("Error");
//    }
//
//    private void checkIfNutritionalValuesExists(final List<NutritionalValueUseDTO> dtoList, final List<? extends BatchNutritionalMappingStrategy> nutritionalList) throws BadRequestException {
//        Set<Long> matchIds = new HashSet<>();
//
//        nutritionalList.forEach(nutritional -> {
//            if (dtoList.stream().anyMatch(item -> item.id().equals(nutritional.getId()))) {
//                matchIds.add(nutritional.getId());
//            }
//        });
//
//        if (!(matchIds.size() == dtoList.size() && matchIds.size() == nutritionalList.size())) {
//            throw new BadRequestException("Nutritional value don't match with nutritional from you request.");
//        }
//    }
//
//    private void checkIfListsIsHasSameSize(final List<NutritionalValueUseDTO> dtoList, final List<? extends BatchNutritionalMappingStrategy> nutritionalList) throws IllegalArgumentException {
//        if (dtoList.size() != nutritionalList.size()) {
//            throw new IllegalArgumentException("Lists is not the same size!");
//        }
//    }
//}
