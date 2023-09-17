package pl.krzychuuweb.labelapp.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface UserQueryRepository extends JpaRepository<User, Long> {

    Optional<User> getByEmail(String email);

    List<User> findAll();
}
