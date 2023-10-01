package pl.krzychuuweb.labelapp.company;

import pl.krzychuuweb.labelapp.company.dto.CompanyCreateDTO;
import pl.krzychuuweb.labelapp.company.dto.CompanyEditDTO;

import java.nio.file.AccessDeniedException;

public interface CompanyFacade {

    Company addCompany(final CompanyCreateDTO companyCreateDTO);

    Company updateCompany(final CompanyEditDTO companyEditDTO) throws AccessDeniedException;

    void deleteCompany(final Long id);
}
