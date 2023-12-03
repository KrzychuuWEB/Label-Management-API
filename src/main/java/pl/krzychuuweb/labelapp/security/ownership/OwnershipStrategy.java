package pl.krzychuuweb.labelapp.security.ownership;

public interface OwnershipStrategy {

    boolean isOwner(Long userId, Long resourceId);
}
