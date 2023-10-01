package pl.krzychuuweb.labelapp.user;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import pl.krzychuuweb.labelapp.company.Company;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String username;

    private String password;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Company> companies = new ArrayList<>();

    User() {
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(final List<Company> companies) {
        this.companies = companies;
    }


    public static final class UserBuilder {
        private Long id;
        private String email;
        private String username;
        private String password;
        private LocalDateTime createdAt;
        private List<Company> companies;

        private UserBuilder() {
        }

        public static UserBuilder anUser() {
            return new UserBuilder();
        }

        public UserBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder withCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserBuilder withCompanies(List<Company> companies) {
            this.companies = companies;
            return this;
        }

        public User build() {
            User user = new User();
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);
            user.setCompanies(companies);
            user.id = this.id;
            user.createdAt = this.createdAt;
            return user;
        }
    }
}
