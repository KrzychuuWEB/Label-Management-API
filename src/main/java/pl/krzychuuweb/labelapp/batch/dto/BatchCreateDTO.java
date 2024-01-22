package pl.krzychuuweb.labelapp.batch.dto;

import java.time.LocalDate;

public record BatchCreateDTO(

        String serial,

        LocalDate expirationDate,

        boolean isShortDate,

        String country
) {
}
