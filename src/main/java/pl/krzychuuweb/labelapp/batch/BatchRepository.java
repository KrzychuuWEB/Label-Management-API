package pl.krzychuuweb.labelapp.batch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BatchRepository extends JpaRepository<Batch, Long> {
}
