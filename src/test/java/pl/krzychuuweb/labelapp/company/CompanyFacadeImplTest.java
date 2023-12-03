package pl.krzychuuweb.labelapp.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.krzychuuweb.labelapp.auth.AuthQueryFacade;
import pl.krzychuuweb.labelapp.company.dto.CompanyCreateDTO;
import pl.krzychuuweb.labelapp.company.dto.CompanyEditDTO;
import pl.krzychuuweb.labelapp.user.User;
import pl.krzychuuweb.labelapp.user.UserQueryFacade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CompanyFacadeImplTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserQueryFacade userQueryFacade;

    @Autowired
    private CompanyQueryFacade companyQueryFacade;

    @Autowired
    private AuthQueryFacade authQueryFacade;

    private CompanyFacade companyFacade;

    @BeforeEach
    void setUp() {
        userQueryFacade = mock(UserQueryFacade.class);
        companyRepository = mock(CompanyRepository.class);
        companyQueryFacade = mock(CompanyQueryFacade.class);
        authQueryFacade = mock(AuthQueryFacade.class);
        companyFacade = new CompanyFacadeImpl(userQueryFacade, companyRepository, companyQueryFacade, authQueryFacade);
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
    void should_edit_company() {
        CompanyEditDTO companyEditDTO = new CompanyEditDTO(1L, "newCompanyName", "newCompanyFooter");
        User user = User.UserBuilder.anUser().withId(1L).withEmail("email2@email.com").build();
        Company company = Company.CompanyBuilder.aCompany().withName(companyEditDTO.name()).withFooter(companyEditDTO.footer()).withUser(user).build();

        when(companyQueryFacade.getById(anyLong())).thenReturn(company);
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