package pl.krzychuuweb.labelapp.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.krzychuuweb.labelapp.company.dto.CompanyCreateDTO;
import pl.krzychuuweb.labelapp.company.dto.CompanyEditDTO;
import pl.krzychuuweb.labelapp.user.User;
import pl.krzychuuweb.labelapp.user.UserQueryFacade;

import java.nio.file.AccessDeniedException;

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

    private CompanyFacade companyFacade;

    @BeforeEach
    void setUp() {
        userQueryFacade = mock(UserQueryFacade.class);
        companyRepository = mock(CompanyRepository.class);
        companyQueryFacade = mock(CompanyQueryFacade.class);
        companyFacade = new CompanyFacadeImpl(userQueryFacade, companyRepository, companyQueryFacade);

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("email@email.com");
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
        User user = User.UserBuilder.anUser().withId(1L).withEmail("email@email.com").build();
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