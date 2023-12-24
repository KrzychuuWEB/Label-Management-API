package pl.krzychuuweb.labelapp.subnutritionalvalue;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.krzychuuweb.labelapp.exceptions.BadRequestException;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.CreateNutritionalValueDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.EditNutritionalValueDTO;
import pl.krzychuuweb.labelapp.subnutritionalvalue.dto.SubNutritionalValueDTO;

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
            @RequestBody CreateNutritionalValueDTO createNutritionalValueDTO
    ) {
        return SubNutritionalValueDTO.mapSubNutritionalValueToDTO(
                subNutritionalValueFacade.add(createNutritionalValueDTO, nutritionalId)
        );
    }

    @PutMapping("/sub/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_ADMIN")
    SubNutritionalValueDTO edit(@PathVariable Long id, @RequestBody EditNutritionalValueDTO editNutritionalValueDTO) {
        if (!id.equals(editNutritionalValueDTO.id())) {
            throw new BadRequestException("Id is not the same!");
        }

        return SubNutritionalValueDTO.mapSubNutritionalValueToDTO(subNutritionalValueFacade.edit(editNutritionalValueDTO));
    }

    @DeleteMapping("/sub/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured("ROLE_ADMIN")
    void delete(@PathVariable Long id) {
        subNutritionalValueFacade.delete(id);
    }
}
