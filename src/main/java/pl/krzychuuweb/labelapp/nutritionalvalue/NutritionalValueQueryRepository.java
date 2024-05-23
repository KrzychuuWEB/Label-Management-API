package pl.krzychuuweb.labelapp.nutritionalvalue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
interface NutritionalValueQueryRepository extends JpaRepository<NutritionalValue, Long> {

    @Query("SELECT nv.priority FROM NutritionalValue nv ORDER BY nv.priority DESC")
    List<BigDecimal> findAllOrderByPriorityDesc();
}
