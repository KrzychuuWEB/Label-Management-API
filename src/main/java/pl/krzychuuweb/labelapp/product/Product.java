package pl.krzychuuweb.labelapp.product;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import pl.krzychuuweb.labelapp.BaseEntity;
import pl.krzychuuweb.labelapp.batch.Batch;
import pl.krzychuuweb.labelapp.security.ownership.OwnershipEntity;
import pl.krzychuuweb.labelapp.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product implements BaseEntity, OwnershipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String composition;

    private String slug;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Batch> batches = new ArrayList<>();

    Product() {
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(final String composition) {
        this.composition = composition;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(final String slug) {
        this.slug = slug;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<Batch> getBatches() {
        return batches;
    }

    public void setBatches(final List<Batch> batches) {
        this.batches = batches;
    }

    public static final class ProductBuilder {
        private Long id;
        private String name;
        private String description;
        private String composition;
        private String slug;
        private User user;

        private ProductBuilder() {
        }

        public static ProductBuilder aProduct() {
            return new ProductBuilder();
        }

        public ProductBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ProductBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder withComposition(String composition) {
            this.composition = composition;
            return this;
        }

        public ProductBuilder withSlug(String slug) {
            this.slug = slug;
            return this;
        }

        public ProductBuilder withUser(User user) {
            this.user = user;
            return this;
        }

        public Product build() {
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setComposition(composition);
            product.setSlug(slug);
            product.setUser(user);
            product.id = this.id;
            return product;
        }
    }
}
