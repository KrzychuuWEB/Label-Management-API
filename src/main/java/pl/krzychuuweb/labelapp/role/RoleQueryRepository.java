package pl.krzychuuweb.labelapp.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface RoleQueryRepository extends JpaRepository<Role, Long> {

    Optional<Role> getByName(final UserRole role);

    @Query("SELECT r FROM User u JOIN u.roles r WHERE u.id = :userId")
    List<Role> getRolesByUserId(@Param("userId") Long userId);
}
