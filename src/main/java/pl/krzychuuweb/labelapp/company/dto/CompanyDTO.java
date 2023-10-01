package pl.krzychuuweb.labelapp.company.dto;

import pl.krzychuuweb.labelapp.company.Company;
import pl.krzychuuweb.labelapp.user.User;

import java.time.LocalDateTime;
import java.util.List;

public record CompanyDTO(
        Long id,
        String name,
        String footer,
        LocalDateTime createdAt,
        User user
) {

    public static CompanyDTO mapCompanyToCompanyDTO(Company company) {
        return new CompanyDTO(company.getId(), company.getName(), company.getFooter(), company.getCreatedAt(), company.getUser());
    }

    public static List<CompanyDTO> mapCompanyListToCompanyDTOList(List<Company> companyList) {
        return companyList.stream().map(company -> new CompanyDTO(
                company.getId(),
                company.getName(),
                company.getFooter(),
                company.getCreatedAt(),
                company.getUser()
        )).toList();
    }
}
