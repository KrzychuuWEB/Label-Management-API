package pl.krzychuuweb.labelapp.nutritionalvalue.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record NutritionalValueUseDTO(

        @NotNull
        @Min(1)
        Long id,

        @NotNull
        @Digits(integer = 6, fraction = 6, message = "Invalid number format")
        @DecimalMax(value = "99999.999999", inclusive = true, message = "Value must be less than or equal to 99999.999999")
        BigDecimal value
) {
}