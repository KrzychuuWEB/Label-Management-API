package pl.krzychuuweb.labelapp.nutritionalvalue;

import java.math.BigDecimal;

public class NutritionalValueTestBuilder {

    private Long id = 1L;
    private String name = "example nutritional value name";
    private BigDecimal priority = new BigDecimal("1.0");

    public static NutritionalValueTestBuilder aNutritionalValue() {
        return new NutritionalValueTestBuilder();
    }

    public NutritionalValueTestBuilder withId(final Long id) {
        this.id = id;
        return this;
    }

    public NutritionalValueTestBuilder withName(final String name) {
        this.name = name;
        return this;
    }

    public NutritionalValueTestBuilder withPriority(final BigDecimal priority) {
        this.priority = priority;
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
