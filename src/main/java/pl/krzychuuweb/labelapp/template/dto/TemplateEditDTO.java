package pl.krzychuuweb.labelapp.template.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record TemplateEditDTO(
        @NotBlank
        Long id,

        @Size(min = 1, max = 80)
        String name,

        @Digits(integer = 6, fraction = 1, message = "Invalid number format")
        @DecimalMax(value = "99999.9", inclusive = true, message = "Value must be less than or equal to 99999.9")
        BigDecimal width,

        @Digits(integer = 6, fraction = 1, message = "Invalid number format")
        @DecimalMax(value = "99999.9", inclusive = true, message = "Value must be less than or equal to 99999.9")
        BigDecimal height
) {
}
