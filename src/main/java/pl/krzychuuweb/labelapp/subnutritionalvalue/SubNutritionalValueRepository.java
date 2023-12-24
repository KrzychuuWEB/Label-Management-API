package pl.krzychuuweb.labelapp.subnutritionalvalue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SubNutritionalValueRepository extends JpaRepository<SubNutritionalValue, Long> {
}
