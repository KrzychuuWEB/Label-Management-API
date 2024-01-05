package pl.krzychuuweb.labelapp.initial;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface InitialRepository extends JpaRepository<Initial, Long> {
}
