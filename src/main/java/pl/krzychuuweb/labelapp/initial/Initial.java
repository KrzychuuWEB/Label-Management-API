package pl.krzychuuweb.labelapp.initial;

import jakarta.persistence.*;
import pl.krzychuuweb.labelapp.user.User;

@Entity
@Table(name = "initials")
public class Initial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    Initial() {
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
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

    public static final class InitialBuilder {
        private Long id;
        private String firstName;
        private String lastName;
        private String name;
        private User user;

        private InitialBuilder() {
        }

        public static InitialBuilder anInitial() {
            return new InitialBuilder();
        }

        public InitialBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public InitialBuilder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public InitialBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public InitialBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public InitialBuilder withUser(User user) {
            this.user = user;
            return this;
        }

        public Initial build() {
            Initial initial = new Initial();
            initial.setFirstName(firstName);
            initial.setLastName(lastName);
            initial.setName(name);
            initial.setUser(user);
            initial.id = this.id;
            return initial;
        }
    }
}
