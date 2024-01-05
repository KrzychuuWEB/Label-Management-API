package pl.krzychuuweb.labelapp.nutritionalvalue.subnutritionalvalue;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.krzychuuweb.labelapp.exception.BadRequestException;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.ChangeNutritionalValuePriorityDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.CreateNutritionalValueDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.EditNutritionalValueDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.subnutritionalvalue.dto.SubNutritionalValueDTO;

import java.util.List;

@RestController
@RequestMapping("/nutritional-values")
class SubNutritionalValueController {

    private final SubNutritionalValueFacade subNutritionalValueFacade;

    SubNutritionalValueController(SubNutritionalValueFacade subNutritionalValueFacade) {
        this.subNutritionalValueFacade = subNutritionalValueFacade;
    }

    @PostMapping("/{nutritionalId}/sub")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured("ROLE_ADMIN")
    SubNutritionalValueDTO create(
            @PathVariable Long nutritionalId,
            @Valid @RequestBody CreateNutritionalValueDTO createNutritionalValueDTO
    ) {
        return SubNutritionalValueDTO.mapSubNutritionalValueToDTO(
                subNutritionalValueFacade.add(createNutritionalValueDTO, nutritionalId)
        );
    }

    @PutMapping("/sub/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_ADMIN")
    SubNutritionalValueDTO edit(@PathVariable Long id, @Valid @RequestBody EditNutritionalValueDTO editNutritionalValueDTO) {
        if (!id.equals(editNutritionalValueDTO.id())) {
            throw new BadRequestException("Id is not the same!");
        }

        return SubNutritionalValueDTO.mapSubNutritionalValueToDTO(subNutritionalValueFacade.edit(editNutritionalValueDTO));
    }

    @PutMapping("/sub/{id}/priority")
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_ADMIN")
    List<SubNutritionalValueDTO> editPriority(@PathVariable Long id, @Valid @RequestBody ChangeNutritionalValuePriorityDTO changeNutritionalValuePriorityDTO) {
        if (!id.equals(changeNutritionalValuePriorityDTO.id())) {
            throw new BadRequestException("Id is not the same!");
        }

        return SubNutritionalValueDTO.mapToSubNutritionalValueList(
                subNutritionalValueFacade.editPriority(changeNutritionalValuePriorityDTO)
        );
    }

    @DeleteMapping("/sub/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured("ROLE_ADMIN")
    void delete(@PathVariable Long id) {
        subNutritionalValueFacade.delete(id);
    }
}
