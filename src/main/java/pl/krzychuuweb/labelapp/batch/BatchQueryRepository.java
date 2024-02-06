package pl.krzychuuweb.labelapp.batch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.krzychuuweb.labelapp.product.Product;

import java.util.List;

@Repository
interface BatchQueryRepository extends JpaRepository<Batch, Long> {

    boolean existsBySerialAndProduct(final String serial, final Product product);

    List<Batch> getAllByProduct(final Product product);
}