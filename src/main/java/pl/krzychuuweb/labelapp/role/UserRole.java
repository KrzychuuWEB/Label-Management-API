package pl.krzychuuweb.labelapp.role;

public enum UserRole {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public String getName() {
        return roleName;
    }

    public static UserRole changeStringToUserRole(String name) {
        for (UserRole role : UserRole.values()) {
            if (role.getName().equals(name)) {
                return role;
            }
        }

        throw new IllegalArgumentException(name + " this user role is not exists");
    }
}
