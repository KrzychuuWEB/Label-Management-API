package pl.krzychuuweb.labelapp.initial;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krzychuuweb.labelapp.user.User;

import java.util.List;

@Repository
interface InitialQueryRepository extends JpaRepository<Initial, Long> {

    List<Initial> findAllByUser(final User user);

    boolean existsByNameAndUser(final String name, final User user);
}
