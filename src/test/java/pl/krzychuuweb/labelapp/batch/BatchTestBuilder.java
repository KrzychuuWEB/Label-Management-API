package pl.krzychuuweb.labelapp.batch;

import java.time.LocalDate;

public class BatchTestBuilder {

    private Long id = 1L;
    private String serial = "serial123";
    private LocalDate expirationDate = LocalDate.now();
    private boolean isShortTime = false;
    private String country = "Poland";

    public static BatchTestBuilder aBatch() {
        return new BatchTestBuilder();
    }

    public Batch build() {
        return Batch.BatchBuilder.aBatch()
                .withId(id)
                .withSerial(serial)
                .withExpirationDate(expirationDate)
                .withCountry(country)
                .withIsShortDate(isShortTime)
                .build();
    }
}
