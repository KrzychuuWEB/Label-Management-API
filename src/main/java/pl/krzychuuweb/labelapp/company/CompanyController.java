package pl.krzychuuweb.labelapp.company;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.krzychuuweb.labelapp.company.dto.CompanyCreateDTO;
import pl.krzychuuweb.labelapp.company.dto.CompanyDTO;
import pl.krzychuuweb.labelapp.company.dto.CompanyEditDTO;
import pl.krzychuuweb.labelapp.exceptions.BadRequestException;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Objects;

import static pl.krzychuuweb.labelapp.company.dto.CompanyDTO.mapCompanyListToCompanyDTOList;
import static pl.krzychuuweb.labelapp.company.dto.CompanyDTO.mapCompanyToCompanyDTO;

@RestController
@RequestMapping("/companies")
class CompanyController {

    private final CompanyQueryFacade companyQueryFacade;

    private final CompanyFacade companyFacade;

    CompanyController(final CompanyQueryFacade companyQueryFacade, final CompanyFacade companyFacade) {
        this.companyQueryFacade = companyQueryFacade;
        this.companyFacade = companyFacade;
    }

    @GetMapping("/{id}")
    CompanyDTO getCompanyById(@PathVariable Long id) {
        return mapCompanyToCompanyDTO(companyQueryFacade.getById(id));
    }

    @GetMapping
    List<CompanyDTO> getAllCompanies() {
        return mapCompanyListToCompanyDTOList(companyQueryFacade.getAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CompanyDTO createCompany(@Valid @RequestBody CompanyCreateDTO companyCreateDTO) {
        return mapCompanyToCompanyDTO(companyFacade.addCompany(companyCreateDTO));
    }

    @PutMapping("/{id}")
    CompanyDTO updateCompany(@Valid @RequestBody CompanyEditDTO companyEditDTO, @PathVariable Long id) throws AccessDeniedException {
        if (!Objects.equals(companyEditDTO.id(), id)) {
            throw new BadRequestException("Id is not this same");
        }

        return mapCompanyToCompanyDTO(companyFacade.updateCompany(companyEditDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCompany(@PathVariable Long id) {
        companyFacade.deleteCompany(id);
    }
}
