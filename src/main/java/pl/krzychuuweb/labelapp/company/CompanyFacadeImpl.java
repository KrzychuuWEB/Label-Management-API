package pl.krzychuuweb.labelapp.company;

import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.auth.AuthQueryFacade;
import pl.krzychuuweb.labelapp.company.dto.CompanyCreateDTO;
import pl.krzychuuweb.labelapp.company.dto.CompanyEditDTO;
import pl.krzychuuweb.labelapp.user.User;
import pl.krzychuuweb.labelapp.user.UserQueryFacade;

@Service
class CompanyFacadeImpl implements CompanyFacade {

    private final UserQueryFacade userQueryFacade;

    private final CompanyRepository companyRepository;

    private final CompanyQueryFacade companyQueryFacade;

    private final AuthQueryFacade authQueryFacade;

    CompanyFacadeImpl(
            final UserQueryFacade userQueryFacade,
            final CompanyRepository companyRepository,
            final CompanyQueryFacade companyQueryFacade,
            final AuthQueryFacade authQueryFacade
    ) {
        this.userQueryFacade = userQueryFacade;
        this.companyRepository = companyRepository;
        this.companyQueryFacade = companyQueryFacade;
        this.authQueryFacade = authQueryFacade;
    }

    @Override
    public Company addCompany(final CompanyCreateDTO companyCreateDTO) {
        User user = userQueryFacade.getUserByEmail(authQueryFacade.getLoggedUserEmail());

        return companyRepository.save(CompanyFactory.createCompany(companyCreateDTO, user));
    }

    @Override
    public Company updateCompany(final CompanyEditDTO companyEditDTO) {
        Company company = companyQueryFacade.getById(companyEditDTO.id());

        company.setName(companyEditDTO.name());
        company.setFooter(companyEditDTO.footer());

        return companyRepository.save(company);
    }

    @Override
    public void deleteCompany(final Long id) {
        Company company = companyQueryFacade.getById(id);

        companyRepository.delete(company);
    }
}
