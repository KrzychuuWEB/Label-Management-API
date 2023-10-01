package pl.krzychuuweb.labelapp.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CompanyQueryRepository extends JpaRepository<Company, Long> {

}
