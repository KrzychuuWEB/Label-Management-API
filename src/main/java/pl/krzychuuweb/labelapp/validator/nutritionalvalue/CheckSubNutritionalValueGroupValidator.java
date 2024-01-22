package pl.krzychuuweb.labelapp.validator.nutritionalvalue;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import pl.krzychuuweb.labelapp.batchnutritionalvalues.dto.BatchNutritionalValuesCreateDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValue;
import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValueQueryFacade;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.NutritionalValueUseDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.subnutritionalvalue.SubNutritionalValue;
import pl.krzychuuweb.labelapp.nutritionalvalue.subnutritionalvalue.SubNutritionalValueQueryFacade;

import java.util.List;

@Component
class CheckSubNutritionalValueGroupValidator implements ConstraintValidator<CheckSubNutritionalValueGroup, BatchNutritionalValuesCreateDTO> {

    private final NutritionalValueQueryFacade nutritionalValueQueryFacade;

    private final SubNutritionalValueQueryFacade subNutritionalValueQueryFacade;

    CheckSubNutritionalValueGroupValidator(NutritionalValueQueryFacade nutritionalValueQueryFacade, SubNutritionalValueQueryFacade subNutritionalValueQueryFacade) {
        this.nutritionalValueQueryFacade = nutritionalValueQueryFacade;
        this.subNutritionalValueQueryFacade = subNutritionalValueQueryFacade;
    }

    @Override
    public boolean isValid(final BatchNutritionalValuesCreateDTO batchNutritionalValuesCreateDTO, final ConstraintValidatorContext constraintValidatorContext) {
        List<Long> nutritionalValuesId = batchNutritionalValuesCreateDTO.nutritionalValueIdList().stream().map(NutritionalValueUseDTO::id).toList();
        List<Long> subNutritionalValuesId = batchNutritionalValuesCreateDTO.subNutritionalValueIdList().stream().map(NutritionalValueUseDTO::id).toList();

        List<NutritionalValue> nutritionalValuesList = nutritionalValueQueryFacade.getAllByListId(nutritionalValuesId);
        List<SubNutritionalValue> subNutritionalValuesList = subNutritionalValueQueryFacade.getAllByListId(subNutritionalValuesId);

        Integer successMatch = 0;

        for (SubNutritionalValue sub : subNutritionalValuesList) {
            for (NutritionalValue nv : nutritionalValuesList) {
                if (nv.getId().equals(sub.getNutritionalValue().getId())) {
                    successMatch++;
                    break;
                }
            }
        }

        return successMatch.equals(subNutritionalValuesList.size());
    }

    @Override
    public void initialize(final CheckSubNutritionalValueGroup constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
