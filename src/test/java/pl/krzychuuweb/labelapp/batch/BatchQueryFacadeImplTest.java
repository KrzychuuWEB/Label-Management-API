package pl.krzychuuweb.labelapp.batch;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.krzychuuweb.labelapp.exception.NotFoundException;
import pl.krzychuuweb.labelapp.product.Product;
import pl.krzychuuweb.labelapp.product.ProductQueryFacade;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@ExtendWith(MockitoExtension.class)
class BatchQueryFacadeImplTest {

    @Mock
    private BatchQueryRepository batchQueryRepository;

    @Mock
    private ProductQueryFacade productQueryFacade;

    @InjectMocks
    private BatchQueryFacadeImpl batchQueryFacade;

    @Test
    void should_check_serial_if_serial_is_not_used_by_product() {
        Product product = Product.ProductBuilder.aProduct().build();

        when(batchQueryRepository.existsBySerialAndProduct(anyString(), any(Product.class))).thenReturn(false);

        boolean result = batchQueryFacade.checkWhetherSerialIsNotUsed("serial", product);

        assertTrue(result);
    }

    @Test
    void should_check_serial_if_serial_is_used_by_product() {
        Product product = Product.ProductBuilder.aProduct().build();

        when(batchQueryRepository.existsBySerialAndProduct(anyString(), any(Product.class))).thenReturn(true);

        boolean result = batchQueryFacade.checkWhetherSerialIsNotUsed("serial", product);

        assertFalse(result);
    }

    @Test
    void should_return_batches_list_by_product() {
        Product product = Product.ProductBuilder.aProduct().build();
        List<Batch> list = List.of(
                Batch.BatchBuilder.aBatch().withProduct(product).build(),
                Batch.BatchBuilder.aBatch().withProduct(product).build(),
                Batch.BatchBuilder.aBatch().withProduct(product).build()
        );

        when(productQueryFacade.getById(anyLong())).thenReturn(product);
        when(batchQueryFacade.getAllBatchesByProduct(anyLong())).thenReturn(list);

        List<Batch> result = batchQueryFacade.getAllBatchesByProduct(anyLong());

        assertThat(result).hasSameSizeAs(list);
    }

    @Test
    void should_return_batch_by_id() {
        Batch batch = Batch.BatchBuilder.aBatch().withId(1L).withSerial("serial123").withCountry("Poland").build();

        when(batchQueryRepository.findById(anyLong())).thenReturn(Optional.of(batch));

        Batch result = batchQueryFacade.getById(anyLong());

        assertThat(result.getId()).isEqualTo(batch.getId());
        assertThat(result.getSerial()).isEqualTo(batch.getSerial());
        assertThat(result.getCountry()).isEqualTo(batch.getCountry());
    }

    @Test
    void should_return_batch_by_id_expected_not_found_exception() {
        when(batchQueryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> batchQueryFacade.getById(anyLong())).isInstanceOf(NotFoundException.class);
    }
}