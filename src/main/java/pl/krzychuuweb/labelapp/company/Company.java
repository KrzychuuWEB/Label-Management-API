package pl.krzychuuweb.labelapp.company;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import pl.krzychuuweb.labelapp.user.User;

import java.time.LocalDateTime;

@Table(name = "companies")
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String footer;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    Company() {
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

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(final String footer) {
        this.footer = footer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public static final class CompanyBuilder {
        private Long id;
        private String name;
        private String footer;
        private User user;
        private LocalDateTime createdAt;

        private CompanyBuilder() {
        }

        public static CompanyBuilder aCompany() {
            return new CompanyBuilder();
        }

        public CompanyBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public CompanyBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CompanyBuilder withFooter(String footer) {
            this.footer = footer;
            return this;
        }

        public CompanyBuilder withUser(User user) {
            this.user = user;
            return this;
        }

        public CompanyBuilder withCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        public Company build() {
            Company company = new Company();
            company.setName(name);
            company.setFooter(footer);
            company.setUser(user);
            company.id = this.id;
            company.createdAt = this.createdAt;
            return company;
        }
    }
}
