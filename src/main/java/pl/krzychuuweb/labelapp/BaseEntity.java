package pl.krzychuuweb.labelapp;

import pl.krzychuuweb.labelapp.user.User;

public interface BaseEntity {

    String getName();

    void setName(final String name);

    User getUser();

    void setUser(final User user);
}
