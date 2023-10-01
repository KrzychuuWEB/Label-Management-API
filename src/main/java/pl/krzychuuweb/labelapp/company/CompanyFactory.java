package pl.krzychuuweb.labelapp.company;

import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.company.dto.CompanyCreateDTO;

@Service
class CompanyFactory {

    CompanyFactory() {
    }

    static Company createCompany(CompanyCreateDTO companyCreateDTO) {
        return Company.CompanyBuilder.aCompany()
                .withName(companyCreateDTO.name())
                .withFooter(companyCreateDTO.footer())
//                .withUser()
                .build();
        // TODO set equals from user principal
    }
}
