package pl.krzychuuweb.labelapp.batch;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValue;
import pl.krzychuuweb.labelapp.nutritionalvalue.subnutritionalvalue.SubNutritionalValue;
import pl.krzychuuweb.labelapp.product.Product;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "batches")
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serial;

    private LocalDate expirationDate;

    private boolean isShortDate;

    private String country;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToMany
    @JoinTable(
            name = "batches_nutritional_values",
            joinColumns = @JoinColumn(name = "batch_id"),
            inverseJoinColumns = @JoinColumn(name = "nutritional_value_id")
    )
    private Set<NutritionalValue> nutritionalValues = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "batches_nutritional_values",
            joinColumns = @JoinColumn(name = "batch_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_nutritional_value_id")
    )
    private Set<SubNutritionalValue> subNutritionalValues = new HashSet<>();

    Batch() {
    }

    public Long getId() {
        return id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(final String serial) {
        this.serial = serial;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(final LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isShortDate() {
        return isShortDate;
    }

    public void setShortDate(final boolean shortDate) {
        isShortDate = shortDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Set<NutritionalValue> getNutritionalValues() {
        return nutritionalValues;
    }

    public void setNutritionalValues(final Set<NutritionalValue> nutritionalValues) {
        this.nutritionalValues = nutritionalValues;
    }

    public Set<SubNutritionalValue> getSubNutritionalValues() {
        return subNutritionalValues;
    }

    public void setSubNutritionalValues(final Set<SubNutritionalValue> subNutritionalValues) {
        this.subNutritionalValues = subNutritionalValues;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(final Product product) {
        this.product = product;
    }

    public static final class BatchBuilder {
        private Long id;
        private String serial;
        private LocalDate expirationDate;
        private boolean isShortDate;
        private String country;
        private Product product;

        private BatchBuilder() {
        }

        public static BatchBuilder aBatch() {
            return new BatchBuilder();
        }

        public BatchBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public BatchBuilder withSerial(String serial) {
            this.serial = serial;
            return this;
        }

        public BatchBuilder withExpirationDate(LocalDate expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public BatchBuilder withIsShortDate(boolean isShortDate) {
            this.isShortDate = isShortDate;
            return this;
        }

        public BatchBuilder withCountry(String country) {
            this.country = country;
            return this;
        }

        public BatchBuilder withProduct(Product product) {
            this.product = product;
            return this;
        }

        public Batch build() {
            Batch batch = new Batch();
            batch.setSerial(serial);
            batch.setExpirationDate(expirationDate);
            batch.setCountry(country);
            batch.setProduct(product);
            batch.id = this.id;
            batch.isShortDate = this.isShortDate;
            return batch;
        }
    }
}
