package pl.krzychuuweb.labelapp.template.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record TemplateEditDTO(
        @NotNull
        @Min(1)
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
