package pl.krzychuuweb.labelapp.nutritionalvalue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PriorityRepository<T extends Priority> extends JpaRepository<T, Long> {
}
