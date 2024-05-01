package pl.krzychuuweb.labelapp.batchnutritionalmapping;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.krzychuuweb.labelapp.batch.Batch;
import pl.krzychuuweb.labelapp.batch.BatchQueryFacade;
import pl.krzychuuweb.labelapp.batchnutritionalmapping.dto.BatchNutritionalMappingCreateDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValue;
import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValueQueryFacade;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.NutritionalValueUseDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.subnutritionalvalue.SubNutritionalValue;
import pl.krzychuuweb.labelapp.nutritionalvalue.subnutritionalvalue.SubNutritionalValueQueryFacade;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BatchNutritionalMappingFacadeImplTest {

    @Mock
    BatchNutritionalMappingRepository batchNutritionalMappingRepository;

    @Mock
    BatchNutritionalMappingQueryFacade batchNutritionalMappingQueryFacade;

    @Mock
    BatchQueryFacade batchQueryFacade;

    @Mock
    NutritionalValueQueryFacade nutritionalValueQueryFacade;

    @Mock
    SubNutritionalValueQueryFacade subNutritionalValueQueryFacade;

    @Mock
    BatchNutritionalMappingFactory batchNutritionalMappingFactory;

    @InjectMocks
    BatchNutritionalMappingFacadeImpl batchNutritionalMappingFacade;

    @Test
    void should_add_nutritional_value_list_for_batch() {
        BatchNutritionalMappingCreateDTO batchNutritionalMappingCreateDTO = new BatchNutritionalMappingCreateDTO(
                List.of(
                        new NutritionalValueUseDTO(1L, "test1"),
                        new NutritionalValueUseDTO(2L, "test2"),
                        new NutritionalValueUseDTO(3L, "test3"),
                        new NutritionalValueUseDTO(4L, "test4")
                ),
                List.of(
                        new NutritionalValueUseDTO(1L, "test1"),
                        new NutritionalValueUseDTO(2L, "test2"),
                        new NutritionalValueUseDTO(3L, "test3"),
                        new NutritionalValueUseDTO(4L, "test4")
                )
        );
        Batch batch = Batch.BatchBuilder.aBatch().build();
        List<NutritionalValue> nutritionalValues = List.of(
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withId(1L).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withId(1L).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withId(1L).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withId(1L).build()
        );
        List<BatchNutritionalMapping> batchNutritionalMappings = List.of(
                BatchNutritionalMapping.BatchNutritionalMappingBuilder.aBatchNutritionalMapping().build(),
                BatchNutritionalMapping.BatchNutritionalMappingBuilder.aBatchNutritionalMapping().build(),
                BatchNutritionalMapping.BatchNutritionalMappingBuilder.aBatchNutritionalMapping().build(),
                BatchNutritionalMapping.BatchNutritionalMappingBuilder.aBatchNutritionalMapping().build()
        );

        when(batchQueryFacade.getById(anyLong())).thenReturn(batch);
        when(nutritionalValueQueryFacade.getAllByListId(anyList())).thenReturn(nutritionalValues);
        when(batchNutritionalMappingRepository.saveAll(anyList())).thenReturn(batchNutritionalMappings);

        List<BatchNutritionalMapping> result = batchNutritionalMappingFacade.add(batchNutritionalMappingCreateDTO, anyLong());

        assertThat(result).hasSameSizeAs(batchNutritionalMappings);
    }

    @Test
    void should_add_nutritional_values_and_sub_nutritional_values_for_batch() {
        BatchNutritionalMappingCreateDTO batchNutritionalMappingCreateDTO = new BatchNutritionalMappingCreateDTO(
                List.of(
                        new NutritionalValueUseDTO(1L, "test1"),
                        new NutritionalValueUseDTO(2L, "test2"),
                        new NutritionalValueUseDTO(3L, "test3"),
                        new NutritionalValueUseDTO(4L, "test4")
                ),
                List.of(
                        new NutritionalValueUseDTO(1L, "test1"),
                        new NutritionalValueUseDTO(2L, "test2"),
                        new NutritionalValueUseDTO(3L, "test3"),
                        new NutritionalValueUseDTO(4L, "test4")
                )
        );
        Batch batch = Batch.BatchBuilder.aBatch().withId(2L).build();

        when(batchNutritionalMappingQueryFacade.existsByBatchId(anyLong())).thenReturn(false);
        when(batchQueryFacade.getById(anyLong())).thenReturn(batch);
        when(nutritionalValueQueryFacade.getAllByListId(anyList())).thenReturn(getNutritionalValueList());
        when(subNutritionalValueQueryFacade.getAllByListId(anyList())).thenReturn(getSubNutritionalValueList());
        when(batchNutritionalMappingFactory.createBatchNutritional(anyList(), anyList(), any(Batch.class))).thenReturn(getBatchNutritionalMappingList(batch));
        when(batchNutritionalMappingFactory.createBatchNutritional(anyList(), anyList(), any(Batch.class))).thenReturn(getBatchNutritionalMappingList(batch));
        when(batchNutritionalMappingRepository.saveAll(anyList())).thenReturn(getBatchNutritionalMappingList(any(Batch.class)));

        List<BatchNutritionalMapping> result = batchNutritionalMappingFacade.add(batchNutritionalMappingCreateDTO ,batch.getId());

        assertThat(result.get(0).getNutritionalValue().getId()).isEqualTo(getNutritionalValueList().get(0).getId());

    }

    private List<BatchNutritionalMapping> getBatchNutritionalMappingList(Batch batch) {
        return List.of(
                BatchNutritionalMapping.BatchNutritionalMappingBuilder.aBatchNutritionalMapping().withBatch(batch).withNutritionalValue(getNutritionalValueList().get(0)).build(),
                BatchNutritionalMapping.BatchNutritionalMappingBuilder.aBatchNutritionalMapping().withBatch(batch).withNutritionalValue(getNutritionalValueList().get(0)).build(),
                BatchNutritionalMapping.BatchNutritionalMappingBuilder.aBatchNutritionalMapping().withBatch(batch).withSubNutritionalValue(getSubNutritionalValueList().get(0)).build()
        );
    }

    private List<Long> getIdsList() {
        return List.of(1L, 2L, 3L);
    }

    private List<NutritionalValue> getNutritionalValueList() {
        return List.of(
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withId(1L).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withId(2L).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withId(3L).build()
        );
    }

    private List<SubNutritionalValue> getSubNutritionalValueList() {
        return List.of(
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().withId(1L).build(),
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().withId(2L).build(),
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().withId(3L).build()
        );
    }
}