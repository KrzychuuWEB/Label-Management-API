package pl.krzychuuweb.labelapp.user;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.krzychuuweb.labelapp.company.Company;
import pl.krzychuuweb.labelapp.role.Role;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String firstName;

    private String password;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Company> companies = new ArrayList<>();

    @ManyToMany(mappedBy = "users")
    private Set<Role> roles = new HashSet<>();

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
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

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> rolesList = new ArrayList<>();

        for (Role role : getRoles()) {
            rolesList.add(new SimpleGrantedAuthority(role.getName().getName()));
        }

        return rolesList;
    }

    public Set<Role> getRoles() {
        return roles != null ? roles : Collections.emptySet();
    }

    public void setRoles(final Set<Role> roles) {
        this.roles = roles;
    }

    public static final class UserBuilder {
        private Long id;
        private String email;
        private String firstName;
        private String password;
        private Set<Role> roles;

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

        public UserBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder withRoles(Set<Role> roles) {
            this.roles = roles;
            return this;
        }

        public User build() {
            User user = new User();
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setPassword(password);
            user.setRoles(roles);
            user.id = this.id;
            return user;
        }
    }
}
