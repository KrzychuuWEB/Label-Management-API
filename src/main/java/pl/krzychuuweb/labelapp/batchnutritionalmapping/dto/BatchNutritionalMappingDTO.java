//package pl.krzychuuweb.labelapp.batchnutritionalmapping.dto;
//
//import pl.krzychuuweb.labelapp.batch.dto.BatchDTO;
//import pl.krzychuuweb.labelapp.batchnutritionalmapping.BatchNutritionalMapping;
//import pl.krzychuuweb.labelapp.nutritionalvalue.dto.NutritionalValueDTO;
//import pl.krzychuuweb.labelapp.nutritionalvalue.subnutritionalvalue.dto.SubNutritionalValueDTO;
//
//public record BatchNutritionalMappingDTO(
//        BatchDTO batchDTO,
//
//        NutritionalValueDTO nutritionalValueDTO,
//
//        SubNutritionalValueDTO subNutritionalValueDTO,
//
//        String value
//) {
//
//    static BatchNutritionalMappingDTO mapToDTO(final BatchNutritionalMapping batchNutritionalMapping) {
//        return new BatchNutritionalMappingDTO(
//                BatchDTO.mapToBatchDTO(batchNutritionalMapping.getBatch()),
//                NutritionalValueDTO.mapNutritionalValueToNutritionalValueDTO(batchNutritionalMapping.getNutritionalValue()),
//                SubNutritionalValueDTO.mapSubNutritionalValueToDTO(batchNutritionalMapping.getSubNutritionalValue()),
//                batchNutritionalMapping.getValue()
//        );
//    }
//}
