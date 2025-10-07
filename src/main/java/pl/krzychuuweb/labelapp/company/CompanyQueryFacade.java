package pl.krzychuuweb.labelapp.company;

import java.util.List;

public interface CompanyQueryFacade {

    Company getById(Long id);

    List<Company> getAll();

    boolean checkWhetherCompanyNameIsNotUsedForLoggedUser(final String name);
}
