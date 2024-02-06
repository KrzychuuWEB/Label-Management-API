package pl.krzychuuweb.labelapp.batch;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.krzychuuweb.labelapp.batch.dto.BatchCreateDTO;
import pl.krzychuuweb.labelapp.batch.dto.BatchEditDTO;
import pl.krzychuuweb.labelapp.exception.BadRequestException;
import pl.krzychuuweb.labelapp.product.Product;
import pl.krzychuuweb.labelapp.product.ProductQueryFacade;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BatchFacadeImplTest {

    @Mock
    private BatchRepository batchRepository;

    @Mock
    private BatchQueryFacade batchQueryFacade;

    @Mock
    private BatchFactory batchFactory;

    @Mock
    private ProductQueryFacade productQueryFacade;

    @InjectMocks
    private BatchFacadeImpl batchFacade;

    @Test
    void should_create_new_batch_for_product_return_batch() {
        Product product = Product.ProductBuilder.aProduct().withId(1L).build();
        BatchCreateDTO batchCreateDTO = new BatchCreateDTO("1234", LocalDate.now(), false, "Poland");
        Batch batch = Batch.BatchBuilder.aBatch()
                .withSerial(batchCreateDTO.serial())
                .withExpirationDate(batchCreateDTO.expirationDate())
                .withIsShortDate(batchCreateDTO.isShortDate())
                .withCountry(batchCreateDTO.country())
                .withProduct(product)
                .build();

        when(productQueryFacade.getById(anyLong())).thenReturn(product);
        when(batchQueryFacade.checkWhetherSerialIsNotUsed(anyString(), any(Product.class))).thenReturn(true);
        when(batchFactory.createNewBatch(any(BatchCreateDTO.class), any(Product.class))).thenReturn(batch);
        when(batchRepository.save(any(Batch.class))).thenReturn(batch);

        Batch result = batchFacade.create(batchCreateDTO, 1L);

        assertThat(result).isInstanceOf(Batch.class);
        assertThat(result.getProduct()).isInstanceOf(Product.class);
        assertThat(result.getSerial()).isEqualTo(batchCreateDTO.serial());
        assertThat(result.getExpirationDate()).isEqualTo(batchCreateDTO.expirationDate());
        Assertions.assertFalse(result.isShortDate());
        assertThat(result.getCountry()).isEqualTo(batchCreateDTO.country());
        assertThat(result.getProduct().getId()).isEqualTo(product.getId());
    }

    @Test
    void should_create_new_batch_for_product_return_bad_request_exception_because_serial_is_used() {
        Product product = Product.ProductBuilder.aProduct().withId(1L).build();
        BatchCreateDTO batchCreateDTO = new BatchCreateDTO("1234", LocalDate.now(), false, "Poland");

        when(productQueryFacade.getById(anyLong())).thenReturn(product);
        when(batchQueryFacade.checkWhetherSerialIsNotUsed(anyString(), any(Product.class))).thenReturn(false);

        assertThatThrownBy(() -> batchFacade.create(batchCreateDTO, 1L)).isInstanceOf(BadRequestException.class);
    }

    @Test
    void should_edit_batch_return_bad_request_exception() {
        BatchEditDTO batchEditDTO = new BatchEditDTO(1L, "new serial", LocalDate.now(), true, "poland");
        Product product = Product.ProductBuilder.aProduct().build();
        Batch batch = Batch.BatchBuilder.aBatch().withSerial("serial").withProduct(product).build();

        when(productQueryFacade.getById(anyLong())).thenReturn(product);
        when(batchQueryFacade.getById(anyLong())).thenReturn(batch);
        when(batchQueryFacade.checkWhetherSerialIsNotUsed(anyString(), any(Product.class))).thenReturn(false);

        assertThatThrownBy(() -> batchFacade.edit(batchEditDTO, anyLong())).isInstanceOf(BadRequestException.class);
    }

    @Test
    void should_edit_batch_by_id() {
        BatchEditDTO batchEditDTO = new BatchEditDTO(1L, "new serial", LocalDate.now(), true, "poland");
        Product product = Product.ProductBuilder.aProduct().build();
        Batch batch = Batch.BatchBuilder.aBatch().withSerial("serial").withProduct(product).build();

        when(productQueryFacade.getById(anyLong())).thenReturn(product);
        when(batchQueryFacade.getById(anyLong())).thenReturn(batch);
        when(batchQueryFacade.checkWhetherSerialIsNotUsed(anyString(), any(Product.class))).thenReturn(true);
        when(batchRepository.save(any(Batch.class))).thenReturn(Batch.BatchBuilder.aBatch()
                .withSerial(batchEditDTO.serial())
                .withIsShortDate(batchEditDTO.isShortDate())
                .withExpirationDate(batchEditDTO.expirationDate())
                .withCountry(batchEditDTO.country())
                .build()
        );

        Batch result = batchFacade.edit(batchEditDTO, anyLong());

        assertThat(result.getSerial()).isEqualTo(batchEditDTO.serial());
        assertThat(result.getExpirationDate()).isEqualTo(batchEditDTO.expirationDate());
        assertThat(result.isShortDate()).isEqualTo(batchEditDTO.isShortDate());
        assertThat(result.getCountry()).isEqualTo(batchEditDTO.country());
    }
}