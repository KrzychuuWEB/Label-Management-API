package pl.krzychuuweb.labelapp.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.krzychuuweb.labelapp.company.dto.CompanyCreateDTO;
import pl.krzychuuweb.labelapp.company.dto.CompanyEditDTO;
import pl.krzychuuweb.labelapp.user.User;

import java.nio.file.AccessDeniedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class CompanyFacadeImplTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyQueryFacade companyQueryFacade;

    private CompanyFacade companyFacade;

    @BeforeEach
    void setUp() {
        companyRepository = mock(CompanyRepository.class);
        companyQueryFacade = mock(CompanyQueryFacade.class);
        companyFacade = new CompanyFacadeImpl(companyRepository, companyQueryFacade);
    }

    @Test
    void should_create_company() {
        Company company = Company.CompanyBuilder.aCompany().withName("companyName").withFooter("companyFooter").build();
        CompanyCreateDTO companyCreateDTO = new CompanyCreateDTO(company.getName(), company.getFooter());

        when(companyRepository.save(any())).thenReturn(company);

        Company result = companyFacade.addCompany(companyCreateDTO);

        assertThat(result.getName()).isEqualTo(company.getName());
        assertThat(result.getFooter()).isEqualTo(company.getFooter());
    }

    @Test
    void should_edit_company() throws AccessDeniedException {
        CompanyEditDTO companyEditDTO = new CompanyEditDTO(1L, "newCompanyName", "newCompanyFooter");
        Company company = Company.CompanyBuilder.aCompany().withName(companyEditDTO.name()).withFooter(companyEditDTO.footer()).build();

        when(companyRepository.save(any())).thenReturn(company);

        Company result = companyFacade.updateCompany(companyEditDTO);

        assertThat(result.getName()).isEqualTo(companyEditDTO.name());
        assertThat(result.getFooter()).isEqualTo(companyEditDTO.footer());
    }

    @Test
    void should_delete_company_by_id() {
        Company company = Company.CompanyBuilder.aCompany().build();
        when(companyQueryFacade.getById(anyLong())).thenReturn(company);

        companyFacade.deleteCompany(anyLong());

        verify(companyRepository, times(1)).delete(any());
    }
}