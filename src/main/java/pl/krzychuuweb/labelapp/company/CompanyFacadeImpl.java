package pl.krzychuuweb.labelapp.company;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.company.dto.CompanyCreateDTO;
import pl.krzychuuweb.labelapp.company.dto.CompanyEditDTO;
import pl.krzychuuweb.labelapp.user.User;
import pl.krzychuuweb.labelapp.user.UserQueryFacade;

import java.nio.file.AccessDeniedException;

@Service
class CompanyFacadeImpl implements CompanyFacade {

    private final UserQueryFacade userQueryFacade;

    private final CompanyRepository companyRepository;

    private final CompanyQueryFacade companyQueryFacade;

    CompanyFacadeImpl(final UserQueryFacade userQueryFacade, final CompanyRepository companyRepository, final CompanyQueryFacade companyQueryFacade) {
        this.userQueryFacade = userQueryFacade;
        this.companyRepository = companyRepository;
        this.companyQueryFacade = companyQueryFacade;
    }

    @Override
    public Company addCompany(final CompanyCreateDTO companyCreateDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userQueryFacade.getUserByEmail(auth.getName());

        return companyRepository.save(CompanyFactory.createCompany(companyCreateDTO, user));
    }

    @Override
    public Company updateCompany(final CompanyEditDTO companyEditDTO) throws AccessDeniedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Company company = companyQueryFacade.getById(companyEditDTO.id());

        if (!company.getUser().getEmail().equals(auth.getName())) {
            throw new AccessDeniedException("You don't have access for this data");
        }

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
