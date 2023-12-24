package pl.krzychuuweb.labelapp.subnutritionalvalue;

import jakarta.persistence.*;
import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValue;

@Entity
@Table(name = "sub_nutritional_values")
public class SubNutritionalValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer priority;

    @ManyToOne
    @JoinColumn(name = "nutritional_value_id")
    private NutritionalValue nutritionalValue;

    SubNutritionalValue() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(final Integer priority) {
        this.priority = priority;
    }

    public NutritionalValue getNutritionalValue() {
        return nutritionalValue;
    }

    public void setNutritionalValue(final NutritionalValue nutritionalValue) {
        this.nutritionalValue = nutritionalValue;
    }


    public static final class SubNutritionalValueBuilder {
        private Long id;
        private String name;
        private Integer priority;
        private NutritionalValue nutritionalValue;

        private SubNutritionalValueBuilder() {
        }

        public static SubNutritionalValueBuilder aSubNutritionalValue() {
            return new SubNutritionalValueBuilder();
        }

        public SubNutritionalValueBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public SubNutritionalValueBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public SubNutritionalValueBuilder withPriority(Integer priority) {
            this.priority = priority;
            return this;
        }

        public SubNutritionalValueBuilder withNutritionalValue(NutritionalValue nutritionalValue) {
            this.nutritionalValue = nutritionalValue;
            return this;
        }

        public SubNutritionalValue build() {
            SubNutritionalValue subNutritionalValue = new SubNutritionalValue();
            subNutritionalValue.setId(id);
            subNutritionalValue.setName(name);
            subNutritionalValue.setPriority(priority);
            subNutritionalValue.setNutritionalValue(nutritionalValue);
            return subNutritionalValue;
        }
    }
}
