package pl.krzychuuweb.labelapp.company;

import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.company.dto.CompanyCreateDTO;
import pl.krzychuuweb.labelapp.user.User;

@Service
class CompanyFactory {

    CompanyFactory() {
    }

    static Company createCompany(CompanyCreateDTO companyCreateDTO, User user) {
        return Company.CompanyBuilder.aCompany()
                .withName(companyCreateDTO.name())
                .withFooter(companyCreateDTO.footer())
                .withUser(user)
                .build();
    }
}
