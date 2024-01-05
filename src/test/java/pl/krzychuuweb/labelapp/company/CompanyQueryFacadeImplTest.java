package pl.krzychuuweb.labelapp.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.krzychuuweb.labelapp.auth.AuthQueryFacade;
import pl.krzychuuweb.labelapp.exception.NotFoundException;
import pl.krzychuuweb.labelapp.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CompanyQueryFacadeImplTest {

    @Autowired
    private CompanyQueryRepository companyQueryRepository;

    @Autowired
    private AuthQueryFacade authQueryFacade;

    private CompanyQueryFacade companyQueryFacade;

    @BeforeEach
    void setUp() {
        companyQueryRepository = mock(CompanyQueryRepository.class);
        authQueryFacade = mock(AuthQueryFacade.class);
        companyQueryFacade = new CompanyQueryFacadeImpl(companyQueryRepository, authQueryFacade);
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

        assertThatThrownBy(() -> companyQueryFacade.getById(anyLong())).isInstanceOf(NotFoundException.class);
    }

    @Test
    void should_get_all_companies() {
        User user = User.UserBuilder.anUser().withEmail("email@email.com").build();
        List<Company> companyList = new ArrayList<>();
        companyList.add(Company.CompanyBuilder.aCompany().withUser(user).build());
        companyList.add(Company.CompanyBuilder.aCompany().withUser(user).build());
        companyList.add(Company.CompanyBuilder.aCompany().withUser(user).build());

        when(authQueryFacade.getLoggedUser()).thenReturn(user);
        when(companyQueryRepository.findAllByUser(any(User.class))).thenReturn(companyList);

        List<Company> result = companyQueryFacade.getAll();

        assertThat(result).hasSameSizeAs(companyList);
    }

    @Test
    void should_check_whether_user_not_used_company_name() {
        User user = User.UserBuilder.anUser().withEmail("email@email.com").build();

        when(authQueryFacade.getLoggedUser()).thenReturn(user);
        when(companyQueryRepository.existsByNameAndUser("test", user)).thenReturn(false);

        boolean result = companyQueryFacade.checkWhetherCompanyNameIsNotUsedForLoggedUser("test");

        assertThat(result).isTrue();
    }

    @Test
    void should_check_whether_user_used_company_name() {
        User user = User.UserBuilder.anUser().withEmail("email@email.com").build();

        when(authQueryFacade.getLoggedUser()).thenReturn(user);
        when(companyQueryRepository.existsByNameAndUser("test", user)).thenReturn(true);

        boolean result = companyQueryFacade.checkWhetherCompanyNameIsNotUsedForLoggedUser("test");

        assertThat(result).isFalse();
    }
}