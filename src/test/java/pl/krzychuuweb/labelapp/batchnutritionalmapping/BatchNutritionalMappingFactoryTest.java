//package pl.krzychuuweb.labelapp.batchnutritionalmapping;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import pl.krzychuuweb.labelapp.batch.Batch;
//import pl.krzychuuweb.labelapp.batch.BatchTestBuilder;
//import pl.krzychuuweb.labelapp.exception.BadRequestException;
//import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValue;
//import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValueTestBuilder;
//import pl.krzychuuweb.labelapp.nutritionalvalue.dto.NutritionalValueUseDTO;
//import pl.krzychuuweb.labelapp.nutritionalvalue.subnutritionalvalue.SubNutritionalValue;
//import pl.krzychuuweb.labelapp.nutritionalvalue.subnutritionalvalue.SubNutritionalValueTestBuilder;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//class BatchNutritionalMappingFactoryTest {
//
//    private BatchNutritionalMappingFactory batchNutritionalMappingFactory;
//
//    @BeforeEach
//    void setUp() {
//        batchNutritionalMappingFactory = new BatchNutritionalMappingFactory();
//    }
//
//    @Test
//    void should_create_batch_nutritional_mapping_by_nutritional_value() {
//        Batch batch = BatchTestBuilder.aBatch().build();
//        List<NutritionalValue> nutritionalValues = List.of(
//                NutritionalValueTestBuilder.aNutritionalValue().withId(1L).build(),
//                NutritionalValueTestBuilder.aNutritionalValue().withId(3L).build(),
//                NutritionalValueTestBuilder.aNutritionalValue().withId(2L).build()
//        );
//        List<NutritionalValueUseDTO> nutritionalValueUseDTOS = List.of(
//                new NutritionalValueUseDTO(1L, "value1"),
//                new NutritionalValueUseDTO(2L, "value2"),
//                new NutritionalValueUseDTO(3L, "value3")
//        );
//
//        List<BatchNutritionalMapping> result = batchNutritionalMappingFactory.createBatchNutritional(
//                nutritionalValues,
//                nutritionalValueUseDTOS,
//                batch
//        );
//
//        assertThat(result).hasSameSizeAs(nutritionalValues);
//    }
//
//    @Test
//    void should_create_batch_nutritional_mapping_by_sub_nutritional_value() {
//        Batch batch = BatchTestBuilder.aBatch().build();
//        List<SubNutritionalValue> subNutritionalValues = List.of(
//                SubNutritionalValueTestBuilder.aSubNutritionalValue().withId(1L).build(),
//                SubNutritionalValueTestBuilder.aSubNutritionalValue().withId(3L).build(),
//                SubNutritionalValueTestBuilder.aSubNutritionalValue().withId(2L).build()
//        );
//
//        List<NutritionalValueUseDTO> nutritionalValueUseDTOS = List.of(
//                new NutritionalValueUseDTO(2L, "value1"),
//                new NutritionalValueUseDTO(3L, "value2"),
//                new NutritionalValueUseDTO(1L, "value3")
//        );
//
//        List<BatchNutritionalMapping> result = batchNutritionalMappingFactory.createBatchNutritional(
//                subNutritionalValues,
//                nutritionalValueUseDTOS,
//                batch
//        );
//
//        assertThat(result).hasSameSizeAs(subNutritionalValues);
//    }
//
//    @Test
//    void lists_should_not_be_the_same_size() {
//        Batch batch = BatchTestBuilder.aBatch().build();
//        List<NutritionalValue> nutritionalValues = List.of(
//                NutritionalValueTestBuilder.aNutritionalValue().withId(1L).build(),
//                NutritionalValueTestBuilder.aNutritionalValue().withId(2L).build(),
//                NutritionalValueTestBuilder.aNutritionalValue().withId(3L).build()
//        );
//        List<NutritionalValueUseDTO> nutritionalValueUseDTOS = List.of(
//                new NutritionalValueUseDTO(1L, "value1"),
//                new NutritionalValueUseDTO(2L, "value2"),
//                new NutritionalValueUseDTO(3L, "value3"),
//                new NutritionalValueUseDTO(4L, "value4")
//        );
//
//        assertThatThrownBy(() ->
//                batchNutritionalMappingFactory.createBatchNutritional(
//                        nutritionalValues,
//                        nutritionalValueUseDTOS,
//                        batch)
//        ).isInstanceOf(IllegalArgumentException.class);
//    }
//
//    @Test
//    void lists_should_not_be_the_same_ids() {
//        Batch batch = BatchTestBuilder.aBatch().build();
//        List<NutritionalValue> nutritionalValues = List.of(
//                NutritionalValueTestBuilder.aNutritionalValue().withId(1L).build(),
//                NutritionalValueTestBuilder.aNutritionalValue().withId(2L).build(),
//                NutritionalValueTestBuilder.aNutritionalValue().withId(4L).build()
//        );
//        List<NutritionalValueUseDTO> nutritionalValueUseDTOS = List.of(
//                new NutritionalValueUseDTO(1L, "value1"),
//                new NutritionalValueUseDTO(2L, "value2"),
//                new NutritionalValueUseDTO(5L, "value3")
//        );
//
//        assertThatThrownBy(() -> batchNutritionalMappingFactory.createBatchNutritional(
//                nutritionalValues,
//                nutritionalValueUseDTOS,
//                batch
//        )).isInstanceOf(BadRequestException.class);
//    }
//}