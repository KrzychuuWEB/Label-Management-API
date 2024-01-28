package pl.krzychuuweb.labelapp.product;

import org.springframework.stereotype.Repository;
import pl.krzychuuweb.labelapp.BaseQueryRepository;
import pl.krzychuuweb.labelapp.security.ownership.OwnershipRepository;
import pl.krzychuuweb.labelapp.user.User;

import java.util.List;
import java.util.Optional;

@Repository
interface ProductQueryRepository extends BaseQueryRepository<Product>, OwnershipRepository<Product> {

    boolean existsBySlugAndUser(final String slug, final User user);

    Optional<Product> getBySlugAndUser(final String slug, final User user);

    List<Product> getAllByUser(final User user);
}
