package pl.krzychuuweb.labelapp.nutritionalvalue;

public class NutritionalValueTestBuilder {

    private Long id = 1L;
    private String name = "example nutritional value name";
    private Integer priority = 1;

    public static NutritionalValueTestBuilder aNutritionalValue() {
        return new NutritionalValueTestBuilder();
    }

    public NutritionalValueTestBuilder withId(final Long id) {
        this.id = id;
        return this;
    }

    public NutritionalValue build() {
        return NutritionalValue.NutritionalValueBuilder.aNutritionalValue()
                .withId(id)
                .withName(name)
                .withPriority(priority)
                .build();
    }
}
