package pl.krzychuuweb.labelapp.company;

import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.company.dto.CompanyCreateDTO;
import pl.krzychuuweb.labelapp.company.dto.CompanyEditDTO;

import java.nio.file.AccessDeniedException;

@Service
public class CompanyFacadeImpl implements CompanyFacade {

    private final CompanyRepository companyRepository;

    private final CompanyQueryFacade companyQueryFacade;

    CompanyFacadeImpl(final CompanyRepository companyRepository, final CompanyQueryFacade companyQueryFacade) {
        this.companyRepository = companyRepository;
        this.companyQueryFacade = companyQueryFacade;
    }

    @Override
    public Company addCompany(final CompanyCreateDTO companyCreateDTO) {
        return companyRepository.save(CompanyFactory.createCompany(companyCreateDTO));
    }

    @Override
    public Company updateCompany(final CompanyEditDTO companyEditDTO) throws AccessDeniedException {
        Company companyFromBD = companyQueryFacade.getById(companyEditDTO.id());

//        if (companyFromBD.getUser().getId().equals(1L)) {
            //TODO set equals from user principal
//            throw new AccessDeniedException("You don't have access for this data");
//        }

        Company company = Company.CompanyBuilder.aCompany()
                .withName(companyEditDTO.name())
                .withFooter(companyEditDTO.footer())
                .build();

        return companyRepository.save(company);
    }

    @Override
    public void deleteCompany(final Long id) {
        Company company = companyQueryFacade.getById(id);

        companyRepository.delete(company);
    }
}
