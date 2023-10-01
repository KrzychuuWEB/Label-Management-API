package pl.krzychuuweb.labelapp.company;

import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.exceptions.NotFoundException;

import java.util.List;

@Service
public class CompanyQueryFacadeImpl implements CompanyQueryFacade {

    private final CompanyQueryRepository companyQueryRepository;

    public CompanyQueryFacadeImpl(final CompanyQueryRepository companyQueryRepository) {
        this.companyQueryRepository = companyQueryRepository;
    }

    @Override
    public Company getById(final Long id) {
        return companyQueryRepository.findById(id).orElseThrow(() -> new NotFoundException("Company not found"));
    }

    @Override
    public List<Company> getAll() {
        return companyQueryRepository.findAll();
    }
}
