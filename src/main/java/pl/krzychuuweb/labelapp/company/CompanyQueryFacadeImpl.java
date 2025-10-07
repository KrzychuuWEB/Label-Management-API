package pl.krzychuuweb.labelapp.company;

import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.auth.AuthQueryFacade;
import pl.krzychuuweb.labelapp.exception.NotFoundException;
import pl.krzychuuweb.labelapp.user.User;

import java.util.List;

@Service
class CompanyQueryFacadeImpl implements CompanyQueryFacade {

    private final CompanyQueryRepository companyQueryRepository;

    private final AuthQueryFacade authQueryFacade;

    public CompanyQueryFacadeImpl(final CompanyQueryRepository companyQueryRepository, AuthQueryFacade authQueryFacade) {
        this.companyQueryRepository = companyQueryRepository;
        this.authQueryFacade = authQueryFacade;
    }

    @Override
    public Company getById(final Long id) {
        return companyQueryRepository.findById(id).orElseThrow(() -> new NotFoundException("Company not found"));
    }

    @Override
    public List<Company> getAll() {
        return companyQueryRepository.findAllByUser(authQueryFacade.getLoggedUser());
    }

    @Override
    public boolean checkWhetherCompanyNameIsNotUsedForLoggedUser(final String name) {
        User user = authQueryFacade.getLoggedUser();

        return !companyQueryRepository.existsByNameAndUser(name, user);
    }
}