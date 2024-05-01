package pl.krzychuuweb.labelapp.batchnutritionalmapping;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.krzychuuweb.labelapp.batch.Batch;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BatchNutritionalMappingQueryFacadeImplTest {

    @Mock
    BatchNutritionalMappingQueryRepository batchNutritionalMappingQueryRepository;

    @InjectMocks
    BatchNutritionalMappingQueryFacadeImpl batchNutritionalValueQueryFacade;

    @Test
    void should_exists_by_batch_id_expected_true() {
        Batch batch = Batch.BatchBuilder.aBatch().build();
        BatchNutritionalMapping.BatchNutritionalMappingBuilder.aBatchNutritionalMapping().withBatch(batch).build();

        when(batchNutritionalMappingQueryRepository.existsByBatchId(anyLong())).thenReturn(true);

        boolean result = batchNutritionalValueQueryFacade.existsByBatchId(anyLong());

        assertTrue(result);
    }

    @Test
    void should_not_exists_by_batch_id_expected_false() {
        Batch batch = Batch.BatchBuilder.aBatch().build();
        BatchNutritionalMapping.BatchNutritionalMappingBuilder.aBatchNutritionalMapping().withBatch(batch).build();

        when(batchNutritionalMappingQueryRepository.existsByBatchId(anyLong())).thenReturn(false);

        boolean result = batchNutritionalValueQueryFacade.existsByBatchId(anyLong());

        assertFalse(result);
    }
}