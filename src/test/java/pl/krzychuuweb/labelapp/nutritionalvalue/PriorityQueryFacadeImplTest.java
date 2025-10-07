package pl.krzychuuweb.labelapp.nutritionalvalue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriorityQueryFacadeImplTest {

    @Mock
    private NutritionalValueQueryRepository nutritionalValueQueryRepository;

    @InjectMocks
    private PriorityQueryFacadeImpl priorityQueryFacade;

    @Test
    void should_get_highest_number_from_sorted_and_filter_list() {
        List<BigDecimal> list = new ArrayList<>();
        list.add(new BigDecimal("1.2"));
        list.add(new BigDecimal("1.0"));
        list.add(new BigDecimal("3.0"));
        list.add(new BigDecimal("2.0"));

        when(nutritionalValueQueryRepository.findAllOrderByPriorityDesc()).thenReturn(list);

        BigDecimal result = priorityQueryFacade.getLastPriorityNumber();

        assertThat(result).isEqualTo(new BigDecimal("3.0"));
    }

    @Test
    void should_return_default_priority_if_list_is_empty() {
        when(nutritionalValueQueryRepository.findAllOrderByPriorityDesc()).thenReturn(new ArrayList<>());

        BigDecimal result = priorityQueryFacade.getLastPriorityNumber();

        assertThat(result).isEqualTo(new BigDecimal("0.0"));
    }
}