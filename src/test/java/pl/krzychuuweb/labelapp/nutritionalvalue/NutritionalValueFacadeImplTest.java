package pl.krzychuuweb.labelapp.nutritionalvalue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.CreateNutritionalValueDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.EditNutritionalValueDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class NutritionalValueFacadeImplTest {

    @Autowired
    private NutritionalValueRepository nutritionalValueRepository;

    @Autowired
    private NutritionalValueQueryFacade nutritionalValueQueryFacade;

    @Autowired
    private NutritionalValueFactory nutritionalValueFactory;

    private NutritionalValueFacade nutritionalValueFacade;

    @BeforeEach
    void setUp() {
        nutritionalValueRepository = mock(NutritionalValueRepository.class);
        nutritionalValueQueryFacade = mock(NutritionalValueQueryFacade.class);
        nutritionalValueFactory = mock(NutritionalValueFactory.class);
        nutritionalValueFacade = new NutritionalValueFacadeImpl(nutritionalValueRepository, nutritionalValueQueryFacade, nutritionalValueFactory);
    }

    @Test
    void should_create_new_nutritional_value() {
        CreateNutritionalValueDTO createNutritionalValueDTO = new CreateNutritionalValueDTO("exampleName", 1);
        NutritionalValue nutritionalValue = NutritionalValue.NutritionalValueBuilder.aNutritionalValue()
                .withName(createNutritionalValueDTO.name())
                .withPriority(createNutritionalValueDTO.priority())
                .build();

        when(nutritionalValueFactory.createNutritionalValue(any(CreateNutritionalValueDTO.class))).thenReturn(nutritionalValue);
        when(nutritionalValueRepository.save(any(NutritionalValue.class))).thenReturn(nutritionalValue);

        NutritionalValue result = nutritionalValueFacade.add(createNutritionalValueDTO);

        assertThat(result.getName()).isEqualTo(createNutritionalValueDTO.name());
        assertThat(result.getPriority()).isEqualTo(createNutritionalValueDTO.priority());
    }

    @Test
    void should_edit_nutritional_value() {
        NutritionalValue nutritionalValue = NutritionalValue.NutritionalValueBuilder
                .aNutritionalValue()
                .withName("exampleName")
                .build();
        EditNutritionalValueDTO editNutritionalValueDTO = new EditNutritionalValueDTO(1L, "newExampleName");
        NutritionalValue newNutritionalValue = NutritionalValue.NutritionalValueBuilder
                .aNutritionalValue()
                .withId(1L)
                .withName(editNutritionalValueDTO.name())
                .build();

        when(nutritionalValueQueryFacade.getById(anyLong())).thenReturn(nutritionalValue);
        when(nutritionalValueRepository.save(any(NutritionalValue.class))).thenReturn(newNutritionalValue);

        NutritionalValue result = nutritionalValueFacade.edit(editNutritionalValueDTO);

        assertThat(result.getId()).isEqualTo(editNutritionalValueDTO.id());
        assertThat(result.getName()).isEqualTo(editNutritionalValueDTO.name());
    }

    @Test
    void should_delete_nutritional_value() {
        NutritionalValue nutritionalValue = NutritionalValue.NutritionalValueBuilder.aNutritionalValue().build();
        when(nutritionalValueQueryFacade.getById(anyLong())).thenReturn(nutritionalValue);

        nutritionalValueFacade.delete(anyLong());

        verify(nutritionalValueRepository, times(1)).delete(nutritionalValue);
    }
}