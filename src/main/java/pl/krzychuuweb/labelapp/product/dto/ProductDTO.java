package pl.krzychuuweb.labelapp.product.dto;

import pl.krzychuuweb.labelapp.product.Product;
import pl.krzychuuweb.labelapp.user.dto.UserDTO;

import java.time.LocalDateTime;
import java.util.List;

public record ProductDTO(
        Long id,

        String name,

        String description,

        String composition,

        String slug,

        LocalDateTime createdAt,

        UserDTO user
) {

    public static ProductDTO mapToProductDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getComposition(),
                product.getSlug(),
                product.getCreatedAt(),
                UserDTO.mapUserToUserDTO(product.getUser())
        );
    }

    public static List<ProductDTO> mapToProductDTOList(List<Product> products) {
        return products.stream().map(product -> new ProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getComposition(),
                        product.getSlug(),
                        product.getCreatedAt(),
                        UserDTO.mapUserToUserDTO(product.getUser())
                ))
                .toList();
    }
}
