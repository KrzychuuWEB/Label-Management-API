package pl.krzychuuweb.labelapp.security.ownership;

import pl.krzychuuweb.labelapp.user.User;

public interface OwnershipEntity {

    Long getId();

    User getUser();

    void setUser(final User user);
}
