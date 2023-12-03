package pl.krzychuuweb.labelapp.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.krzychuuweb.labelapp.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CompanyQueryFacadeImplTest {

    @Autowired
    private CompanyQueryRepository companyQueryRepository;

    private CompanyQueryFacade companyQueryFacade;

    @BeforeEach
    void setUp() {
        companyQueryRepository = mock(CompanyQueryRepository.class);
        companyQueryFacade = new CompanyQueryFacadeImpl(companyQueryRepository);
    }

    @Test
    void should_get_company_by_id() {
        Company company = Company.CompanyBuilder.aCompany().withId(1L).withName("company").build();

        when(companyQueryRepository.findById(anyLong())).thenReturn(Optional.of(company));

        Company result = companyQueryFacade.getById(anyLong());

        assertThat(result.getId()).isEqualTo(company.getId());
        assertThat(result.getName()).isEqualTo(company.getName());
    }

    @Test
    void should_get_company_by_id_throw_not_found_exception() {
        when(companyQueryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> companyQueryFacade.getById(anyLong()));
    }

    @Test
    void should_get_all_companies() {
        List<Company> companyList = new ArrayList<>();
        companyList.add(Company.CompanyBuilder.aCompany().build());
        companyList.add(Company.CompanyBuilder.aCompany().build());
        companyList.add(Company.CompanyBuilder.aCompany().build());

        when(companyQueryRepository.findAll()).thenReturn(companyList);

        List<Company> result = companyQueryFacade.getAll();

        assertThat(result).hasSize(3);
    }
}