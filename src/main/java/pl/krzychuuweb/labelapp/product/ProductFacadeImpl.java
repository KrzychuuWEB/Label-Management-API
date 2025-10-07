package pl.krzychuuweb.labelapp.product;

import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.auth.AuthQueryFacade;
import pl.krzychuuweb.labelapp.exception.BadRequestException;
import pl.krzychuuweb.labelapp.product.dto.ProductCreateDTO;
import pl.krzychuuweb.labelapp.product.dto.ProductEditDTO;
import pl.krzychuuweb.labelapp.user.User;

@Service
class ProductFacadeImpl implements ProductFacade {

    private final ProductRepository productRepository;

    private final ProductQueryFacade productQueryFacade;

    private final ProductFactory productFactory;

    private final AuthQueryFacade authQueryFacade;

    private final ProductService productService;

    ProductFacadeImpl(ProductRepository productRepository, ProductQueryFacade productQueryFacade, ProductFactory productFactory, AuthQueryFacade authQueryFacade, ProductService productService) {
        this.productRepository = productRepository;
        this.productQueryFacade = productQueryFacade;
        this.productFactory = productFactory;
        this.authQueryFacade = authQueryFacade;
        this.productService = productService;
    }

    @Override
    public Product create(final ProductCreateDTO productCreateDTO) {
        if (!productQueryFacade.checkWhetherNameInResourceIsNotUsedByLoggedUser(productCreateDTO.name())) {
            throw new BadRequestException("You will not create new product because name is taken!");
        }

        User user = authQueryFacade.getLoggedUser();

        return productRepository.save(
                productFactory.createProduct(productCreateDTO, user)
        );
    }

    @Override
    public Product edit(final ProductEditDTO productEditDTO) {
        Product product = productQueryFacade.getById(productEditDTO.id());

        if (!productQueryFacade.checkWhetherNameInResourceIsNotUsedByLoggedUser(productEditDTO.name())) {
            throw new BadRequestException("You will not edit product because name is taken!");
        }

        product.setName(productEditDTO.name());
        product.setSlug(productService.generateSlug(productEditDTO.name()));
        product.setComposition(productEditDTO.composition());
        product.setDescription(productEditDTO.description());

        return productRepository.save(product);
    }

    @Override
    public void deleteById(final Long id) {
        Product product = productQueryFacade.getById(id);

        productRepository.delete(product);
    }
}
