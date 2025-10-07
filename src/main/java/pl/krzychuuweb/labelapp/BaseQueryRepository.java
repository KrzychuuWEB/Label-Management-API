package pl.krzychuuweb.labelapp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import pl.krzychuuweb.labelapp.user.User;

import java.util.List;

@NoRepositoryBean
public interface BaseQueryRepository<T extends BaseEntity> extends JpaRepository<T, Long> {

    List<T> findAllByUser(final User user);

    boolean existsByNameAndUser(final String name, final User user);
}
