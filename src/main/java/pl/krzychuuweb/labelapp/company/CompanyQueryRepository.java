package pl.krzychuuweb.labelapp.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krzychuuweb.labelapp.user.User;

import java.util.List;

@Repository
interface CompanyQueryRepository extends JpaRepository<Company, Long> {

    List<Company> findAllByUser(final User user);

    boolean existsByNameAndUser(final String name, final User user);
}
