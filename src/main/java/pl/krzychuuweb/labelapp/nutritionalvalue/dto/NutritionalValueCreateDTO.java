package pl.krzychuuweb.labelapp.nutritionalvalue.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NutritionalValueCreateDTO(

        @NotBlank
        @Size(max = 50)
        String name
) {

}
