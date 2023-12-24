package pl.krzychuuweb.labelapp.nutritionalvalue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface NutritionalValueQueryRepository extends JpaRepository<NutritionalValue, Long> {

    boolean existsByPriority(Integer priority);
}
