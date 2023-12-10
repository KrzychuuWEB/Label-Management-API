package pl.krzychuuweb.labelapp.role;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import pl.krzychuuweb.labelapp.user.User;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserRole name;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<User> users = new HashSet<>();

    Role() {
    }

    public Long getId() {
        return id;
    }

    public UserRole getName() {
        return name;
    }

    public void setName(final UserRole name) {
        this.name = name;
    }

    LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(final Set<User> users) {
        this.users = users;
    }


    public static final class RoleBuilder {
        private Long id;
        private UserRole name;

        public RoleBuilder() {
        }

        public static RoleBuilder aRole() {
            return new RoleBuilder();
        }

        public RoleBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public RoleBuilder withName(UserRole name) {
            this.name = name;
            return this;
        }

        public RoleBuilder but() {
            return aRole().withId(id).withName(name);
        }

        public Role build() {
            Role role = new Role();
            role.setName(name);
            role.id = this.id;
            return role;
        }
    }
}
