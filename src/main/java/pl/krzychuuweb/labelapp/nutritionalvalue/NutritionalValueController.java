package pl.krzychuuweb.labelapp.nutritionalvalue;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.krzychuuweb.labelapp.exception.BadRequestException;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.NutritionalValueCreateDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.NutritionalValueEditDTO;

import java.util.List;

@RestController
@RequestMapping("/nutritional-values")
class NutritionalValueController {

    private final NutritionalValueFacade nutritionalValueFacade;

    private final NutritionalValueQueryFacade nutritionalValueQueryFacade;

    NutritionalValueController(final NutritionalValueFacade nutritionalValueFacade, final NutritionalValueQueryFacade nutritionalValueQueryFacade) {
        this.nutritionalValueFacade = nutritionalValueFacade;
        this.nutritionalValueQueryFacade = nutritionalValueQueryFacade;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    NutritionalValue create(@Valid @RequestBody NutritionalValueCreateDTO nutritionalValueCreateDTO) {
        return nutritionalValueFacade.create(nutritionalValueCreateDTO);
    }

    @GetMapping
    List<NutritionalValue> getAll() {
        return nutritionalValueQueryFacade.getAll();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    NutritionalValue edit(
            @Valid @RequestBody NutritionalValueEditDTO nutritionalValueEditDTO,
            @PathVariable Long id
    ) {
        if (!id.equals(nutritionalValueEditDTO.id())) {
            throw new BadRequestException("The ids is not same");
        }

        return nutritionalValueFacade.edit(nutritionalValueEditDTO, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable Long id) {
        nutritionalValueFacade.deleteById(id);
    }
}
