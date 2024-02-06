package pl.krzychuuweb.labelapp.batch.dto;

import pl.krzychuuweb.labelapp.batch.Batch;
import pl.krzychuuweb.labelapp.product.dto.ProductDTO;

import java.time.LocalDate;
import java.util.List;

public record BatchDTO(
        Long id,

        String serial,

        LocalDate expirationDate,

        boolean isShortDate,

        String country,

        ProductDTO productDTO
) {

    public static BatchDTO mapToBatchDTO(final Batch batch) {
        return new BatchDTO(
                batch.getId(),
                batch.getSerial(),
                batch.getExpirationDate(),
                batch.isShortDate(),
                batch.getCountry(),
                ProductDTO.mapToProductDTO(batch.getProduct())
        );
    }

    public static List<BatchDTO> mapToBatchDTOList(final List<Batch> batches) {
        return batches.stream().map(batch ->
                new BatchDTO(
                        batch.getId(),
                        batch.getSerial(),
                        batch.getExpirationDate(),
                        batch.isShortDate(),
                        batch.getCountry(),
                        ProductDTO.mapToProductDTO(batch.getProduct())
                )
        ).toList();
    }
}
