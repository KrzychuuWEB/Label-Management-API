package pl.krzychuuweb.labelapp.nutritionalvalue;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.krzychuuweb.labelapp.exceptions.BadRequestException;
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
    NutritionalValueDTO create(@RequestBody CreateNutritionalValueDTO createNutritionalValueDTO) {
        return NutritionalValueDTO.mapNutritionalValueToNutritionalValueDTO(
                nutritionalValueFacade.add(createNutritionalValueDTO)
        );
    }

    @PutMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.OK)
    NutritionalValueDTO edit(@PathVariable Long id, @RequestBody EditNutritionalValueDTO editNutritionalValueDTO) {
        if (!id.equals(editNutritionalValueDTO.id())) {
            throw new BadRequestException("Id is not the same");
        }

        return NutritionalValueDTO.mapNutritionalValueToNutritionalValueDTO(
                nutritionalValueFacade.edit(editNutritionalValueDTO)
        );
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        nutritionalValueFacade.delete(id);
    }
}
