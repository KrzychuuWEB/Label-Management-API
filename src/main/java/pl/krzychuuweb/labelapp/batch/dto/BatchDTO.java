package pl.krzychuuweb.labelapp.batch.dto;

import pl.krzychuuweb.labelapp.batch.Batch;

import java.time.LocalDate;

public record BatchDTO(
        Long id,

        String serial,

        LocalDate expirationDate,

        boolean isShortDate,

        String country
) {

    public static BatchDTO mapToBatchDTO(final Batch batch) {
        return new BatchDTO(
                batch.getId(),
                batch.getSerial(),
                batch.getExpirationDate(),
                batch.isShortDate(),
                batch.getCountry()
        );
    }
}
