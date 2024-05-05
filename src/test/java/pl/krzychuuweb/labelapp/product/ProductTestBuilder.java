package pl.krzychuuweb.labelapp.product;

public class ProductTestBuilder {
    private Long id = 1L;

    public ProductTestBuilder aProduct() {
        return new ProductTestBuilder();
    }

    public ProductTestBuilder withId(final Long id) {
        this.id = id;
        return this;
    }

    public Product build() {
        return Product.ProductBuilder.aProduct()
                .withId(id)
                .build();
    }
}
