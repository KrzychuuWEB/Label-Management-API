package pl.krzychuuweb.labelapp.company;

import pl.krzychuuweb.labelapp.company.dto.CompanyCreateDTO;
import pl.krzychuuweb.labelapp.company.dto.CompanyEditDTO;

public interface CompanyFacade {

    Company addCompany(final CompanyCreateDTO companyCreateDTO);

    Company updateCompany(final CompanyEditDTO companyEditDTO);

    void deleteCompany(final Long id);
}
