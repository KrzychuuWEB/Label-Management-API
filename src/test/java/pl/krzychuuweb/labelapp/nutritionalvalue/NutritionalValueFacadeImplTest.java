package pl.krzychuuweb.labelapp.nutritionalvalue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.krzychuuweb.labelapp.exceptions.BadRequestException;
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

    @Test
    void should_edit_priority_is_success_priority_is_asc() {
        ChangeNutritionalValuePriorityDTO changeNutritionalValuePriorityDTO = new ChangeNutritionalValuePriorityDTO(2L, 4);
        NutritionalValue nutritionalValue = NutritionalValue.NutritionalValueBuilder.aNutritionalValue()
                .withId(2L)
                .withPriority(2)
                .build();

        List<NutritionalValue> listFromDb = List.of(
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(2).withId(2L).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(3).withId(3L).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(4).withId(4L).build()
        );

        when(nutritionalValueQueryFacade.getById(anyLong())).thenReturn(nutritionalValue);
        when(nutritionalValueQueryFacade.getAllByPriorityBetweenRange(2, 4)).thenReturn(listFromDb);
        when(nutritionalValueRepository.saveAll(any())).thenReturn(listFromDb);

        List<NutritionalValue> listToResult = List.of(
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(1).withId(1L).build(),
                listFromDb.get(0),
                listFromDb.get(1),
                listFromDb.get(2),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(5).withId(5L).build()
        );

        when(nutritionalValueQueryFacade.getAll()).thenReturn(listToResult);
        List<NutritionalValue> result = nutritionalValueFacade.editPriority(changeNutritionalValuePriorityDTO);

        assertThat(result).hasSize(5);
        assertThat(result)
                .extracting(NutritionalValue::getId, NutritionalValue::getPriority)
                .contains(tuple(1L, 1));
        assertThat(result)
                .extracting(NutritionalValue::getId, NutritionalValue::getPriority)
                .contains(tuple(2L, 4));
        assertThat(result)
                .extracting(NutritionalValue::getId, NutritionalValue::getPriority)
                .contains(tuple(3L, 2));
        assertThat(result)
                .extracting(NutritionalValue::getId, NutritionalValue::getPriority)
                .contains(tuple(4L, 3));
        assertThat(result)
                .extracting(NutritionalValue::getId, NutritionalValue::getPriority)
                .contains(tuple(5L, 5));
    }

    @Test
    void should_edit_priority_is_success_priority_is_desc() {
        ChangeNutritionalValuePriorityDTO changeNutritionalValuePriorityDTO = new ChangeNutritionalValuePriorityDTO(4L, 2);
        NutritionalValue nutritionalValue = NutritionalValue.NutritionalValueBuilder.aNutritionalValue()
                .withId(4L)
                .withPriority(4)
                .build();

        List<NutritionalValue> listFromDb = List.of(
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(2).withId(2L).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(3).withId(3L).build(),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(4).withId(4L).build()
        );

        when(nutritionalValueQueryFacade.getById(anyLong())).thenReturn(nutritionalValue);
        when(nutritionalValueQueryFacade.getAllByPriorityBetweenRange(2, 4)).thenReturn(listFromDb);
        when(nutritionalValueRepository.saveAll(any())).thenReturn(listFromDb);

        List<NutritionalValue> listToResult = List.of(
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(1).withId(1L).build(),
                listFromDb.get(0),
                listFromDb.get(1),
                listFromDb.get(2),
                NutritionalValue.NutritionalValueBuilder.aNutritionalValue().withPriority(5).withId(5L).build()
        );

        when(nutritionalValueQueryFacade.getAll()).thenReturn(listToResult);
        List<NutritionalValue> result = nutritionalValueFacade.editPriority(changeNutritionalValuePriorityDTO);

        assertThat(result).hasSize(5);
        assertThat(result)
                .extracting(NutritionalValue::getId, NutritionalValue::getPriority)
                .contains(tuple(1L, 1));
        assertThat(result)
                .extracting(NutritionalValue::getId, NutritionalValue::getPriority)
                .contains(tuple(2L, 3));
        assertThat(result)
                .extracting(NutritionalValue::getId, NutritionalValue::getPriority)
                .contains(tuple(3L, 4));
        assertThat(result)
                .extracting(NutritionalValue::getId, NutritionalValue::getPriority)
                .contains(tuple(4L, 2));
        assertThat(result)
                .extracting(NutritionalValue::getId, NutritionalValue::getPriority)
                .contains(tuple(5L, 5));
    }

    @Test
    void should_edit_priority_is_failed() {
        ChangeNutritionalValuePriorityDTO changeNutritionalValuePriorityDTO = new ChangeNutritionalValuePriorityDTO(1L, 2);
        NutritionalValue nutritionalValue = NutritionalValue.NutritionalValueBuilder.aNutritionalValue()
                .withId(1L)
                .withPriority(2)
                .build();

        when(nutritionalValueQueryFacade.getById(anyLong())).thenReturn(nutritionalValue);
        assertThatThrownBy(() -> nutritionalValueFacade.editPriority(changeNutritionalValuePriorityDTO)).isInstanceOf(BadRequestException.class);
    }
}