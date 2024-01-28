package pl.krzychuuweb.labelapp.batch;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.krzychuuweb.labelapp.product.Product;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

@ExtendWith(MockitoExtension.class)
class BatchQueryFacadeImplTest {

    @Mock
    private BatchQueryRepository batchQueryRepository;

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
}