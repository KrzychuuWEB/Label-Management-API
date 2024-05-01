package pl.krzychuuweb.labelapp.batchnutritionalmapping;

import org.springframework.stereotype.Service;

@Service
class BatchNutritionalMappingQueryFacadeImpl implements BatchNutritionalMappingQueryFacade {

    private final BatchNutritionalMappingQueryRepository batchNutritionalMappingQueryRepository;

    BatchNutritionalMappingQueryFacadeImpl(BatchNutritionalMappingQueryRepository batchNutritionalMappingQueryRepository) {
        this.batchNutritionalMappingQueryRepository = batchNutritionalMappingQueryRepository;
    }

    @Override
    public boolean existsByBatchId(final Long id) {
        return batchNutritionalMappingQueryRepository.existsByBatchId(id);
    }
}
