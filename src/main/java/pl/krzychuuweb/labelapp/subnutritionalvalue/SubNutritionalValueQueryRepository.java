package pl.krzychuuweb.labelapp.subnutritionalvalue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SubNutritionalValueQueryRepository extends JpaRepository<SubNutritionalValue, Long> {

    boolean existsByPriority(final Integer priority);
}
