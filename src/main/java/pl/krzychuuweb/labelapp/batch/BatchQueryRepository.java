package pl.krzychuuweb.labelapp.batch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krzychuuweb.labelapp.user.User;

@Repository
interface BatchQueryRepository extends JpaRepository<Batch, Long> {

    boolean existsBySerialAndUser(final String serial, final User user);
}
