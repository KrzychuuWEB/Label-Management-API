package pl.krzychuuweb.labelapp.template;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.krzychuuweb.labelapp.exception.BadRequestException;
import pl.krzychuuweb.labelapp.security.ownership.CheckOwnership;
import pl.krzychuuweb.labelapp.template.dto.TemplateCreateDTO;
import pl.krzychuuweb.labelapp.template.dto.TemplateDTO;
import pl.krzychuuweb.labelapp.template.dto.TemplateEditDTO;

import java.util.List;

import static pl.krzychuuweb.labelapp.template.dto.TemplateDTO.mapToTemplateDTO;
import static pl.krzychuuweb.labelapp.template.dto.TemplateDTO.mapToTemplateDTOList;

@RestController
@RequestMapping("/templates")
class TemplateController {

    private final TemplateQueryFacade templateQueryFacade;

    private final TemplateFacade templateFacade;

    TemplateController(TemplateQueryFacade templateQueryFacade, TemplateFacade templateFacade) {
        this.templateQueryFacade = templateQueryFacade;
        this.templateFacade = templateFacade;
    }

    @GetMapping
    List<TemplateDTO> getAll() {
        return mapToTemplateDTOList(templateQueryFacade.getAllByLoggedUser());
    }

    @GetMapping("/{id}")
    @CheckOwnership(TemplateOwnershipStrategy.class)
    TemplateDTO getById(@PathVariable Long id) {
        return mapToTemplateDTO(templateQueryFacade.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    TemplateDTO add(@Valid @RequestBody TemplateCreateDTO templateCreateDTO) {
        return mapToTemplateDTO(templateFacade.create(templateCreateDTO));
    }

    @PutMapping("/{id}")
    @CheckOwnership(TemplateOwnershipStrategy.class)
    TemplateDTO edit(@Valid @RequestBody TemplateEditDTO templateEditDTO, @PathVariable Long id) throws BadRequestException {
        if (!templateEditDTO.id().equals(id)) {
            throw new BadRequestException("The id is not same!");
        }

        return mapToTemplateDTO(templateFacade.edit(templateEditDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CheckOwnership(TemplateOwnershipStrategy.class)
    void deleteById(@PathVariable Long id) {
        templateFacade.deleteById(id);
    }
}
