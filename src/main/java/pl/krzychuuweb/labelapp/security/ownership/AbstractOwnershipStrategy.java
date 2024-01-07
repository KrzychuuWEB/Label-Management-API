package pl.krzychuuweb.labelapp.security.ownership;

public class AbstractOwnershipStrategy<T extends OwnershipEntity> implements OwnershipStrategy {

    private final OwnershipRepository<T> ownershipRepository;

    protected AbstractOwnershipStrategy(OwnershipRepository<T> ownershipRepository) {
        this.ownershipRepository = ownershipRepository;
    }

    @Override
    public boolean isOwner(final Long userId, final Long resourceId) {
        return ownershipRepository.getById(resourceId).getUser().getId().equals(userId);
    }
}
