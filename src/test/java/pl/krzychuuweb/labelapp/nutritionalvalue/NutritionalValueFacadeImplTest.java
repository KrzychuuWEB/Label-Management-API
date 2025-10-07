package pl.krzychuuweb.labelapp.nutritionalvalue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.NutritionalValueCreateDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.NutritionalValueEditDTO;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NutritionalValueFacadeImplTest {

    @Mock
    private NutritionalValueRepository nutritionalValueRepository;

    @Mock
    private NutritionalValueQueryFacade nutritionalValueQueryFacade;

    @Mock
    private PriorityQueryFacade priorityQueryFacade;

    @Mock
    private NutritionalValueFactory nutritionalValueFactory;

    @InjectMocks
    private NutritionalValueFacadeImpl nutritionalValueFacade;

    @Test
    void should_create_new_nutritional_value_with_priority() {
        NutritionalValueCreateDTO nutritionalValueCreateDTO = new NutritionalValueCreateDTO("example name");
        NutritionalValue nutritionalValue = NutritionalValueTestBuilder.aNutritionalValue().withName(nutritionalValueCreateDTO.name()).build();

        when(priorityQueryFacade.getLastPriorityNumber()).thenReturn(new BigDecimal("1.0"));
        when(nutritionalValueFactory.createNutritionalValue(any(NutritionalValueCreateDTO.class), any(BigDecimal.class))).thenReturn(nutritionalValue);
        when(nutritionalValueRepository.save(any(NutritionalValue.class))).thenReturn(nutritionalValue);

        NutritionalValue result = nutritionalValueFacade.create(nutritionalValueCreateDTO);

        assertThat(result.getPriority()).isEqualTo(new BigDecimal("1.0"));
        assertThat(result.getName()).isEqualTo(nutritionalValueCreateDTO.name());
    }

    @Test
    void should_edit_nutritional_value_by_id() {
        NutritionalValue nutritionalValue = NutritionalValueTestBuilder.aNutritionalValue().build();
        NutritionalValueEditDTO nutritionalValueEditDTO = new NutritionalValueEditDTO(nutritionalValue.getId(), "example new name");
        NutritionalValue editNutritionalValue = NutritionalValue.NutritionalValueBuilder.aNutritionalValue()
                .withName(nutritionalValueEditDTO.name())
                .build();

        when(nutritionalValueQueryFacade.getById(anyLong())).thenReturn(nutritionalValue);
        when(nutritionalValueRepository.save(any(NutritionalValue.class))).thenReturn(editNutritionalValue);

        NutritionalValue result = nutritionalValueFacade.edit(nutritionalValueEditDTO, nutritionalValue.getId());

        assertThat(result.getName()).isEqualTo(nutritionalValueEditDTO.name());
    }
}