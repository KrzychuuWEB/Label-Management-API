package pl.krzychuuweb.labelapp.batchnutritionalmapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BatchNutritionalMappingQueryRepository extends JpaRepository<BatchNutritionalMapping, Long> {

    boolean existsByBatchId(final Long id);
}
