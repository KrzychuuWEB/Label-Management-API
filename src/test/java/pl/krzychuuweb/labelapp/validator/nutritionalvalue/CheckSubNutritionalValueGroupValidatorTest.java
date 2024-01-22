package pl.krzychuuweb.labelapp.validator.nutritionalvalue;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.krzychuuweb.labelapp.batchnutritionalvalues.dto.BatchNutritionalValuesCreateDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValue;
import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValueQueryFacade;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.NutritionalValueUseDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.subnutritionalvalue.SubNutritionalValue;
import pl.krzychuuweb.labelapp.nutritionalvalue.subnutritionalvalue.SubNutritionalValueQueryFacade;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckSubNutritionalValueGroupValidatorTest {

    @Mock
    private CheckSubNutritionalValueGroup checkSubNutritionalValueGroup;

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private NutritionalValueQueryFacade nutritionalValueQueryFacade;

    @Mock
    private SubNutritionalValueQueryFacade subNutritionalValueQueryFacade;

    @InjectMocks
    private CheckSubNutritionalValueGroupValidator checkSubNutritionalValueGroupValidator;

    @Test
    void should_return_true() {
        BatchNutritionalValuesCreateDTO batchCreateDTO = new BatchNutritionalValuesCreateDTO(
                List.of(
                        new NutritionalValueUseDTO(1L, new BigDecimal("2"))
                ),
                List.of(
                        new NutritionalValueUseDTO(1L, new BigDecimal("2"))
                ));

        List<NutritionalValue> nutritionalValueList = List.of(
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withId(1L).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withId(2L).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withId(3L).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withId(4L).build()
        );

        List<SubNutritionalValue> subNutritionalValueList = List.of(
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().withId(1L).withNutritionalValue(getNutritionalValueFromList(nutritionalValueList, 4L)).build(),
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().withId(2L).withNutritionalValue(getNutritionalValueFromList(nutritionalValueList, 3L)).build(),
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().withId(3L).withNutritionalValue(getNutritionalValueFromList(nutritionalValueList, 2L)).build(),
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().withId(3L).withNutritionalValue(getNutritionalValueFromList(nutritionalValueList, 1L)).build(),
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().withId(4L).withNutritionalValue(getNutritionalValueFromList(nutritionalValueList, 1L)).build()
        );

        when(nutritionalValueQueryFacade.getAllByListId(any())).thenReturn(nutritionalValueList);
        when(subNutritionalValueQueryFacade.getAllByListId(any())).thenReturn(subNutritionalValueList);
        checkSubNutritionalValueGroupValidator = new CheckSubNutritionalValueGroupValidator(nutritionalValueQueryFacade, subNutritionalValueQueryFacade);
        checkSubNutritionalValueGroupValidator.initialize(checkSubNutritionalValueGroup);

        assertTrue(checkSubNutritionalValueGroupValidator.isValid(batchCreateDTO, context));
    }

    @Test
    void should_return_false() {
        BatchNutritionalValuesCreateDTO batchCreateDTO = new BatchNutritionalValuesCreateDTO(
                List.of(
                        new NutritionalValueUseDTO(1L, new BigDecimal("2"))
                ),
                List.of(
                        new NutritionalValueUseDTO(1L, new BigDecimal("2"))
                ));

        List<NutritionalValue> nutritionalValueList = List.of(
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withId(1L).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withId(2L).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withId(3L).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withId(4L).build()
        );

        NutritionalValue badNutritionalValue = NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withId(5L).build();

        List<SubNutritionalValue> subNutritionalValueList = List.of(
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().withId(1L).withNutritionalValue(badNutritionalValue).build(),
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().withId(2L).withNutritionalValue(getNutritionalValueFromList(nutritionalValueList, 3L)).build(),
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().withId(3L).withNutritionalValue(getNutritionalValueFromList(nutritionalValueList, 2L)).build(),
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().withId(4L).withNutritionalValue(getNutritionalValueFromList(nutritionalValueList, 1L)).build(),
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().withId(5L).withNutritionalValue(getNutritionalValueFromList(nutritionalValueList, 1L)).build()
        );

        when(nutritionalValueQueryFacade.getAllByListId(any())).thenReturn(nutritionalValueList);
        when(subNutritionalValueQueryFacade.getAllByListId(any())).thenReturn(subNutritionalValueList);
        checkSubNutritionalValueGroupValidator = new CheckSubNutritionalValueGroupValidator(nutritionalValueQueryFacade, subNutritionalValueQueryFacade);
        checkSubNutritionalValueGroupValidator.initialize(checkSubNutritionalValueGroup);

        assertFalse(checkSubNutritionalValueGroupValidator.isValid(batchCreateDTO, context));
    }

    private NutritionalValue getNutritionalValueFromList(final List<NutritionalValue> list, final Long nutritionalId) {
        return list.stream().filter(nv -> nv.getId().equals(nutritionalId)).findFirst().orElse(null);
    }
}