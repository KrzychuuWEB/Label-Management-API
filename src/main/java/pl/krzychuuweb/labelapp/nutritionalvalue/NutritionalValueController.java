package pl.krzychuuweb.labelapp.nutritionalvalue;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.krzychuuweb.labelapp.exception.BadRequestException;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.ChangeNutritionalValuePriorityDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.CreateNutritionalValueDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.EditNutritionalValueDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.NutritionalValueDTO;

import java.util.List;

@RestController
@RequestMapping("/nutritional-values")
class NutritionalValueController {

    private final NutritionalValueQueryFacade nutritionalValueQueryFacade;

    private final NutritionalValueFacade nutritionalValueFacade;

    NutritionalValueController(final NutritionalValueQueryFacade nutritionalValueQueryFacade, NutritionalValueFacade nutritionalValueFacade) {
        this.nutritionalValueQueryFacade = nutritionalValueQueryFacade;
        this.nutritionalValueFacade = nutritionalValueFacade;
    }

    @GetMapping
    List<NutritionalValueDTO> getAll() {
        return NutritionalValueDTO.mapNutritionalValueListToNutritionalValueDTO(
                nutritionalValueQueryFacade.getAll()
        );
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    NutritionalValueDTO create(@Valid @RequestBody CreateNutritionalValueDTO createNutritionalValueDTO) {
        return NutritionalValueDTO.mapNutritionalValueToNutritionalValueDTO(
                nutritionalValueFacade.add(createNutritionalValueDTO)
        );
    }

    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.OK)
    NutritionalValueDTO edit(@PathVariable Long id, @Valid @RequestBody EditNutritionalValueDTO editNutritionalValueDTO) {
        if (!id.equals(editNutritionalValueDTO.id())) {
            throw new BadRequestException("Id is not the same");
        }

        return NutritionalValueDTO.mapNutritionalValueToNutritionalValueDTO(
                nutritionalValueFacade.edit(editNutritionalValueDTO)
        );
    }

    @PutMapping("/{id}/priority")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.OK)
    List<NutritionalValueDTO> editPriority(@PathVariable Long id, @Valid @RequestBody ChangeNutritionalValuePriorityDTO changeNutritionalValuePriorityDTO) {
        if (!id.equals(changeNutritionalValuePriorityDTO.id())) {
            throw new BadRequestException("Id is not the same");
        }

        return NutritionalValueDTO.mapNutritionalValueListToNutritionalValueDTO(
                nutritionalValueFacade.editPriority(changeNutritionalValuePriorityDTO)
        );
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        nutritionalValueFacade.delete(id);
    }
}
