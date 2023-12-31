package pl.krzychuuweb.labelapp.nutritionalvalue;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import pl.krzychuuweb.labelapp.subnutritionalvalue.SubNutritionalValue;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "nutritional_values")
public class NutritionalValue implements Priority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer priority;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "nutritionalValue")
    private List<SubNutritionalValue> subNutritionalValues;

    NutritionalValue() {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<SubNutritionalValue> getSubNutritionalValues() {
        return subNutritionalValues;
    }

    public void setSubNutritionalValues(final List<SubNutritionalValue> subNutritionalValues) {
        this.subNutritionalValues = subNutritionalValues;
    }

    public static final class NutritionalValueBuilder {
        private Long id;
        private String name;
        private Integer priority;

        private NutritionalValueBuilder() {
        }

        public static NutritionalValueBuilder aNutritionalValue() {
            return new NutritionalValueBuilder();
        }

        public NutritionalValueBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public NutritionalValueBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public NutritionalValueBuilder withPriority(Integer priority) {
            this.priority = priority;
            return this;
        }

        public NutritionalValue build() {
            NutritionalValue nutritionalValue = new NutritionalValue();
            nutritionalValue.setId(id);
            nutritionalValue.setName(name);
            nutritionalValue.setPriority(priority);
            return nutritionalValue;
        }
    }
}
