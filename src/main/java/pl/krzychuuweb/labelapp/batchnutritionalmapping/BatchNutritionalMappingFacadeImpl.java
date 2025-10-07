//package pl.krzychuuweb.labelapp.batchnutritionalmapping;
//
//import org.springframework.stereotype.Service;
//import pl.krzychuuweb.labelapp.batch.Batch;
//import pl.krzychuuweb.labelapp.batch.BatchQueryFacade;
//import pl.krzychuuweb.labelapp.batchnutritionalmapping.dto.BatchNutritionalMappingCreateDTO;
//import pl.krzychuuweb.labelapp.exception.BadRequestException;
//import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValue;
//import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValueQueryFacade;
//import pl.krzychuuweb.labelapp.nutritionalvalue.dto.NutritionalValueUseDTO;
//import pl.krzychuuweb.labelapp.nutritionalvalue.subnutritionalvalue.SubNutritionalValue;
//import pl.krzychuuweb.labelapp.nutritionalvalue.subnutritionalvalue.SubNutritionalValueQueryFacade;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//class BatchNutritionalMappingFacadeImpl implements BatchNutritionalMappingFacade {
//
//    private final BatchNutritionalMappingRepository batchNutritionalMappingRepository;
//
//    private final BatchNutritionalMappingQueryFacade batchNutritionalMappingQueryFacade;
//
//    private final BatchQueryFacade batchQueryFacade;
//
//    private final NutritionalValueQueryFacade nutritionalValueQueryFacade;
//
//    private final SubNutritionalValueQueryFacade subNutritionalValueQueryFacade;
//
//    private final BatchNutritionalMappingFactory batchNutritionalMappingFactory;
//
//    BatchNutritionalMappingFacadeImpl(
//            final BatchNutritionalMappingRepository batchNutritionalMappingRepository,
//            final BatchNutritionalMappingQueryFacade batchNutritionalMappingQueryFacade,
//            final BatchQueryFacade batchQueryFacade, NutritionalValueQueryFacade nutritionalValueQueryFacade,
//            final SubNutritionalValueQueryFacade subNutritionalValueQueryFacade,
//            final BatchNutritionalMappingFactory batchNutritionalMappingFactory
//    ) {
//        this.batchNutritionalMappingRepository = batchNutritionalMappingRepository;
//        this.batchNutritionalMappingQueryFacade = batchNutritionalMappingQueryFacade;
//        this.batchQueryFacade = batchQueryFacade;
//        this.nutritionalValueQueryFacade = nutritionalValueQueryFacade;
//        this.subNutritionalValueQueryFacade = subNutritionalValueQueryFacade;
//        this.batchNutritionalMappingFactory = batchNutritionalMappingFactory;
//    }
//
//    @Override
//    public List<BatchNutritionalMapping> add(final BatchNutritionalMappingCreateDTO createDTO, final Long batchId) {
//        if (batchNutritionalMappingQueryFacade.existsByBatchId(batchId)) {
//            throw new BadRequestException("This batch has nutritional values");
//        }
//
//        List<BatchNutritionalMapping> nutritionalMappingList = new ArrayList<>();
//        Batch batch = batchQueryFacade.getById(batchId);
//        List<Long> nutritionalValueIds = createDTO.nutritionalValueIdList().stream().map(NutritionalValueUseDTO::id).toList();
//        List<Long> subNutritionalValueIds = createDTO.subNutritionalValueIdList().stream().map(NutritionalValueUseDTO::id).toList();
//        List<NutritionalValue> nutritionalValues = nutritionalValueQueryFacade.getAllByListId(nutritionalValueIds);
//        List<SubNutritionalValue> subNutritionalValues = subNutritionalValueQueryFacade.getAllByListId(subNutritionalValueIds);
//
//        nutritionalMappingList.addAll(
//                batchNutritionalMappingFactory.createBatchNutritional(nutritionalValues, createDTO.nutritionalValueIdList(), batch)
//        );
//
//        if (!createDTO.subNutritionalValueIdList().isEmpty()) {
//            nutritionalMappingList.addAll(
//                    batchNutritionalMappingFactory.createBatchNutritional(subNutritionalValues, createDTO.subNutritionalValueIdList(), batch)
//            );
//        }
//
//        return batchNutritionalMappingRepository.saveAll(nutritionalMappingList);
//    }
//}
