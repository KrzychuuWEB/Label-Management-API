package pl.krzychuuweb.labelapp.nutritionalvalue;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "nutritional_values")
public class NutritionalValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal priority;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;

    NutritionalValue() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public BigDecimal getPriority() {
        return priority;
    }

    public void setPriority(final BigDecimal priority) {
        this.priority = priority;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(final boolean delete) {
        isDeleted = delete;
    }

    public static final class NutritionalValueBuilder {
        private Long id;
        private String name;
        private BigDecimal priority;

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

        public NutritionalValueBuilder withPriority(BigDecimal priority) {
            this.priority = priority;
            return this;
        }

        public NutritionalValue build() {
            NutritionalValue nutritionalValue = new NutritionalValue();
            nutritionalValue.setName(name);
            nutritionalValue.setPriority(priority);
            nutritionalValue.id = this.id;
            return nutritionalValue;
        }
    }
}
