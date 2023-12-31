package pl.krzychuuweb.labelapp.nutritionalvalue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface PriorityQueryRepository<T extends Priority> extends JpaRepository<T, Long> {

    boolean existsByPriority(Integer priority);

    @Query("SELECT nv FROM #{#entityName} nv WHERE nv.priority BETWEEN :minPriority AND :maxPriority")
    List<T> findAllByPriorityBetween(final Integer minPriority, final Integer maxPriority);
}
