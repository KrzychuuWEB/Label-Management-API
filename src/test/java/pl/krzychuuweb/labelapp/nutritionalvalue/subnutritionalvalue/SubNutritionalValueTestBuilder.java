package pl.krzychuuweb.labelapp.nutritionalvalue.subnutritionalvalue;

public class SubNutritionalValueTestBuilder {
    private Long id = 1L;
    private String name = "example sub nutritional value name";
    private Integer priority = 1;

    public static SubNutritionalValueTestBuilder aSubNutritionalValue() {
        return new SubNutritionalValueTestBuilder();
    }

    public SubNutritionalValueTestBuilder withId(final Long id) {
        this.id = id;
        return this;
    }

    public SubNutritionalValue build() {
        return SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue()
                .withId(id)
                .withName(name)
                .withPriority(priority)
                .build();
    }
}
