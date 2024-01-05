package pl.krzychuuweb.labelapp.nutritionalvalue.subnutritionalvalue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.krzychuuweb.labelapp.exception.BadRequestException;
import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValue;
import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValueQueryFacade;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.ChangeNutritionalValuePriorityDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.CreateNutritionalValueDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.EditNutritionalValueDTO;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Java6Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

    @Test
    void should_edit_priority_is_success_priority_is_asc() {
        ChangeNutritionalValuePriorityDTO changeNutritionalValuePriorityDTO = new ChangeNutritionalValuePriorityDTO(2L, 4);
        SubNutritionalValue nutritionalValue = SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue()
                .withId(2L)
                .withPriority(2)
                .build();

        List<SubNutritionalValue> listFromDb = List.of(
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().withPriority(2).withId(2L).build(),
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().withPriority(3).withId(3L).build(),
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().withPriority(4).withId(4L).build()
        );

        when(subNutritionalValueQueryFacade.getById(anyLong())).thenReturn(nutritionalValue);
        when(subNutritionalValueQueryFacade.getAllByPriorityBetweenRange(2, 4)).thenReturn(listFromDb);
        when(subNutritionalValueRepository.saveAll(any())).thenReturn(listFromDb);

        List<SubNutritionalValue> listToResult = List.of(
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().withPriority(1).withId(1L).build(),
                listFromDb.get(0),
                listFromDb.get(1),
                listFromDb.get(2),
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().withPriority(5).withId(5L).build()
        );

        when(subNutritionalValueQueryFacade.getAll()).thenReturn(listToResult);
        List<SubNutritionalValue> result = subNutritionalValueFacade.editPriority(changeNutritionalValuePriorityDTO);

        assertThat(result).hasSize(5);
        assertThat(result)
                .extracting(SubNutritionalValue::getId, SubNutritionalValue::getPriority)
                .contains(tuple(1L, 1));
        assertThat(result)
                .extracting(SubNutritionalValue::getId, SubNutritionalValue::getPriority)
                .contains(tuple(2L, 4));
        assertThat(result)
                .extracting(SubNutritionalValue::getId, SubNutritionalValue::getPriority)
                .contains(tuple(3L, 2));
        assertThat(result)
                .extracting(SubNutritionalValue::getId, SubNutritionalValue::getPriority)
                .contains(tuple(4L, 3));
        assertThat(result)
                .extracting(SubNutritionalValue::getId, SubNutritionalValue::getPriority)
                .contains(tuple(5L, 5));
    }

    @Test
    void should_edit_priority_is_success_priority_is_dsc() {
        ChangeNutritionalValuePriorityDTO changeNutritionalValuePriorityDTO = new ChangeNutritionalValuePriorityDTO(4L, 2);
        SubNutritionalValue nutritionalValue = SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue()
                .withId(4L)
                .withPriority(4)
                .build();

        List<SubNutritionalValue> listFromDb = List.of(
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().withPriority(2).withId(2L).build(),
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().withPriority(3).withId(3L).build(),
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().withPriority(4).withId(4L).build()
        );

        when(subNutritionalValueQueryFacade.getById(anyLong())).thenReturn(nutritionalValue);
        when(subNutritionalValueQueryFacade.getAllByPriorityBetweenRange(2, 4)).thenReturn(listFromDb);
        when(subNutritionalValueRepository.saveAll(any())).thenReturn(listFromDb);

        List<SubNutritionalValue> listToResult = List.of(
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().withPriority(1).withId(1L).build(),
                listFromDb.get(0),
                listFromDb.get(1),
                listFromDb.get(2),
                SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue().withPriority(5).withId(5L).build()
        );

        when(subNutritionalValueQueryFacade.getAll()).thenReturn(listToResult);
        List<SubNutritionalValue> result = subNutritionalValueFacade.editPriority(changeNutritionalValuePriorityDTO);

        assertThat(result).hasSize(5);
        assertThat(result)
                .extracting(SubNutritionalValue::getId, SubNutritionalValue::getPriority)
                .contains(tuple(1L, 1));
        assertThat(result)
                .extracting(SubNutritionalValue::getId, SubNutritionalValue::getPriority)
                .contains(tuple(2L, 3));
        assertThat(result)
                .extracting(SubNutritionalValue::getId, SubNutritionalValue::getPriority)
                .contains(tuple(3L, 4));
        assertThat(result)
                .extracting(SubNutritionalValue::getId, SubNutritionalValue::getPriority)
                .contains(tuple(4L, 2));
        assertThat(result)
                .extracting(SubNutritionalValue::getId, SubNutritionalValue::getPriority)
                .contains(tuple(5L, 5));
    }

    @Test
    void should_edit_priority_is_failed() {
        ChangeNutritionalValuePriorityDTO changeNutritionalValuePriorityDTO = new ChangeNutritionalValuePriorityDTO(1L, 2);
        SubNutritionalValue nutritionalValue = SubNutritionalValue.SubNutritionalValueBuilder.aSubNutritionalValue()
                .withId(1L)
                .withPriority(2)
                .build();

        when(subNutritionalValueQueryFacade.getById(anyLong())).thenReturn(nutritionalValue);
        assertThatThrownBy(() -> subNutritionalValueFacade.editPriority(changeNutritionalValuePriorityDTO)).isInstanceOf(BadRequestException.class);
    }
}