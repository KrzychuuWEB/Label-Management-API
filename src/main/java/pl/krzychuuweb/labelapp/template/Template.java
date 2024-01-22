package pl.krzychuuweb.labelapp.template;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import pl.krzychuuweb.labelapp.BaseEntity;
import pl.krzychuuweb.labelapp.security.ownership.OwnershipEntity;
import pl.krzychuuweb.labelapp.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "templates")
public class Template implements BaseEntity, OwnershipEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(precision = 6, scale = 1)
    private BigDecimal width;

    @Column(precision = 6, scale = 1)
    private BigDecimal height;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    Template() {
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

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(final BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(final BigDecimal height) {
        this.height = height;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(final User user) {
        this.user = user;
    }

    public static final class TemplateBuilder {
        private Long id;
        private String name;
        private BigDecimal width;
        private BigDecimal height;
        private User user;

        private TemplateBuilder() {
        }

        public static TemplateBuilder aTemplate() {
            return new TemplateBuilder();
        }

        public TemplateBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public TemplateBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public TemplateBuilder withWidth(BigDecimal width) {
            this.width = width;
            return this;
        }

        public TemplateBuilder withHeight(BigDecimal height) {
            this.height = height;
            return this;
        }

        public TemplateBuilder withUser(User user) {
            this.user = user;
            return this;
        }

        public Template build() {
            Template template = new Template();
            template.setId(id);
            template.setName(name);
            template.setWidth(width);
            template.setHeight(height);
            template.setUser(user);
            return template;
        }
    }
}
