package pl.krzychuuweb.labelapp.validator.nutritionalvalue;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckSubNutritionalValueGroupValidator.class)
public @interface CheckSubNutritionalValueGroup {
    String message() default "No match with sub nutritional value for nutritional value!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
