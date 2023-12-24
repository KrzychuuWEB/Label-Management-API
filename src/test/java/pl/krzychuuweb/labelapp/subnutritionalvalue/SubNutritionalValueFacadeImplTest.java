package pl.krzychuuweb.labelapp.subnutritionalvalue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValue;
import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValueQueryFacade;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.CreateNutritionalValueDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.EditNutritionalValueDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class SubNutritionalValueFacadeImplTest {

    @Autowired
    private SubNutritionalValueRepository subNutritionalValueRepository;

    @Autowired
    private SubNutritionalValueFactory subNutritionalValueFactory;

    @Autowired
    private NutritionalValueQueryFacade nutritionalValueQueryFacade;

    @Autowired
    private SubNutritionalValueQueryFacade subNutritionalValueQueryFacade;

    private SubNutritionalValueFacade subNutritionalValueFacade;

    @BeforeEach
    void setup() {
        subNutritionalValueRepository = mock(SubNutritionalValueRepository.class);
        subNutritionalValueFactory = mock(SubNutritionalValueFactory.class);
        nutritionalValueQueryFacade = mock(NutritionalValueQueryFacade.class);
        subNutritionalValueQueryFacade = mock(SubNutritionalValueQueryFacade.class);
        subNutritionalValueFacade = new SubNutritionalValueFacadeImpl(
                subNutritionalValueRepository,
                subNutritionalValueFactory,
                nutritionalValueQueryFacade,
                subNutritionalValueQueryFacade
        );
    }

    @Test
    void should_create_new_sub_nutritional_value() {
        CreateNutritionalValueDTO createNutritionalValueDTO = new CreateNutritionalValueDTO("newSubNutritionalValue", 1);
        NutritionalValue nutritionalValue = NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withId(1L).withName("nutritionalValue").withPriority(1).build();
        SubNutritionalValue subNutritionalValue = SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue()
                .withName(createNutritionalValueDTO.name())
                .withPriority(createNutritionalValueDTO.priority())
                .withNutritionalValue(nutritionalValue)
                .build();

        when(subNutritionalValueQueryFacade.checkWhetherPriorityIsNotUsed(anyInt())).thenReturn(true);
        when(nutritionalValueQueryFacade.getById(anyLong())).thenReturn(nutritionalValue);
        when(subNutritionalValueFactory.createSubNutritionalValue(any(CreateNutritionalValueDTO.class), any(NutritionalValue.class))).thenReturn(subNutritionalValue);
        when(subNutritionalValueRepository.save(any(SubNutritionalValue.class))).thenReturn(subNutritionalValue);

        SubNutritionalValue result = subNutritionalValueFacade.add(createNutritionalValueDTO, anyLong());

        assertThat(result.getNutritionalValue().getName()).isEqualTo(nutritionalValue.getName());
        assertThat(result.getName()).isEqualTo(createNutritionalValueDTO.name());
        assertThat(result.getPriority()).isEqualTo(createNutritionalValueDTO.priority());
    }

    @Test
    void should_edit_sub_nutritional_value() {
        EditNutritionalValueDTO editNutritionalValueDTO = new EditNutritionalValueDTO(1L, "newName");
        SubNutritionalValue subNutritionalValue = SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().build();
        SubNutritionalValue subNutritionalValueEdit = SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue()
                .withName(editNutritionalValueDTO.name())
                .build();

        when(subNutritionalValueQueryFacade.getById(anyLong())).thenReturn(subNutritionalValue);
        when(subNutritionalValueRepository.save(any(SubNutritionalValue.class))).thenReturn(subNutritionalValueEdit);

        SubNutritionalValue result = subNutritionalValueFacade.edit(editNutritionalValueDTO);

        assertThat(result.getName()).isEqualTo(subNutritionalValue.getName());
    }

    @Test
    void should_delete_sub_nutritional_value() {
        SubNutritionalValue subNutritionalValue = SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().build();
        when(subNutritionalValueQueryFacade.getById(anyLong())).thenReturn(subNutritionalValue);

        subNutritionalValueRepository.delete(subNutritionalValue);

        verify(subNutritionalValueRepository, times(1)).delete(subNutritionalValue);
    }
}