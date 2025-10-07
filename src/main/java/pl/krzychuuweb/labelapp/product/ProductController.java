package pl.krzychuuweb.labelapp.product;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.krzychuuweb.labelapp.exception.BadRequestException;
import pl.krzychuuweb.labelapp.product.dto.ProductCreateDTO;
import pl.krzychuuweb.labelapp.product.dto.ProductDTO;
import pl.krzychuuweb.labelapp.product.dto.ProductEditDTO;
import pl.krzychuuweb.labelapp.security.ownership.CheckOwnership;

import java.util.List;

@RestController
@RequestMapping("/products")
class ProductController {

    private final ProductQueryFacade productQueryFacade;

    private final ProductFacade productFacade;

    ProductController(ProductQueryFacade productQueryFacade, ProductFacade productFacade) {
        this.productQueryFacade = productQueryFacade;
        this.productFacade = productFacade;
    }

    @GetMapping("/{slug}")
    ProductDTO getBySlug(@PathVariable String slug) {
        return ProductDTO.mapToProductDTO(productQueryFacade.getProductBySlugAndAuthUser(slug));
    }

    @GetMapping
    List<ProductDTO> getAll() {
        return ProductDTO.mapToProductDTOList(productQueryFacade.getAllProductByAuthUser());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ProductDTO add(@Valid @RequestBody ProductCreateDTO productCreateDTO) {
        return ProductDTO.mapToProductDTO(productFacade.create(productCreateDTO));
    }

    @PutMapping("/{id}")
    @CheckOwnership(ProductOwnershipStrategy.class)
    ProductDTO edit(@Valid @RequestBody ProductEditDTO productEditDTO, @PathVariable Long id) {
        if (!id.equals(productEditDTO.id())) {
            throw new BadRequestException("The id is not same");
        }

        return ProductDTO.mapToProductDTO(productFacade.edit(productEditDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckOwnership(ProductOwnershipStrategy.class)
    void delete(@PathVariable Long id) {
        productFacade.deleteById(id);
    }
}
