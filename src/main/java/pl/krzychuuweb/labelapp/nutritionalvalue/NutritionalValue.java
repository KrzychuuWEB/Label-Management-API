package pl.krzychuuweb.labelapp.nutritionalvalue;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "nutritional_values")
public class NutritionalValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Float priority;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

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

    public Float getPriority() {
        return priority;
    }

    public void setPriority(final Float priority) {
        this.priority = priority;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static final class NutritionalValueBuilder {
        private Long id;
        private String name;
        private Float priority;

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

        public NutritionalValueBuilder withPriority(Float priority) {
            this.priority = priority;
            return this;
        }

        public NutritionalValueBuilder but() {
            return aNutritionalValue().withId(id).withName(name).withPriority(priority);
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
