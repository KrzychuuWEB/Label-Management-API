package pl.krzychuuweb.labelapp.initial;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.krzychuuweb.labelapp.exception.BadRequestException;
import pl.krzychuuweb.labelapp.initial.dto.InitialCreateDTO;
import pl.krzychuuweb.labelapp.initial.dto.InitialDTO;
import pl.krzychuuweb.labelapp.initial.dto.InitialEditDTO;
import pl.krzychuuweb.labelapp.security.ownership.CheckOwnership;

import java.util.List;

@RestController
@RequestMapping("/initials")
class InitialController {

    private final InitialQueryFacade initialQueryFacade;

    private final InitialFacade initialFacade;

    InitialController(InitialQueryFacade initialQueryFacade, InitialFacade initialFacade) {
        this.initialQueryFacade = initialQueryFacade;
        this.initialFacade = initialFacade;
    }

    @GetMapping
    List<InitialDTO> getAllForLoggedUser() {
        return InitialDTO.mapInitialsToInitialsDTO(initialQueryFacade.getAllByLoggedUser());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    InitialDTO create(@Valid @RequestBody InitialCreateDTO initialCreateDTO) {
        return InitialDTO.mapInitialToInitialDTO(initialFacade.add(initialCreateDTO));
    }

    @PutMapping("/{id}")
    @CheckOwnership(InitialOwnershipStrategy.class)
    InitialDTO edit(@Valid @RequestBody InitialEditDTO initialEditDTO, @PathVariable Long id) throws BadRequestException {
        if (!initialEditDTO.id().equals(id)) {
            throw new BadRequestException("The initial id is not same");
        }

        return InitialDTO.mapInitialToInitialDTO(initialFacade.edit(initialEditDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckOwnership(InitialOwnershipStrategy.class)
    void delete(@PathVariable Long id) {
        initialFacade.deleteById(id);
    }
}
