package pl.krzychuuweb.labelapp.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface UserQueryRepository extends JpaRepository<User, Long> {

    Optional<User> getByEmail(final String email);

    List<User> findAll();

    boolean existsByUsername(final String username);

    boolean existsByEmail(final String email);
}
