package pl.krzychuuweb.labelapp.security.ownership;

public interface OwnershipRepository<T extends OwnershipEntity> {

    T getById(final Long id);
}
