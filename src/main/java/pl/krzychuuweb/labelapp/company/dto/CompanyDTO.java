package pl.krzychuuweb.labelapp.company.dto;

import pl.krzychuuweb.labelapp.company.Company;
import pl.krzychuuweb.labelapp.user.dto.UserDTO;

import java.time.LocalDateTime;
import java.util.List;

public record CompanyDTO(
        Long id,
        String name,
        String footer,
        LocalDateTime createdAt,
        UserDTO user
) {

    public static CompanyDTO mapCompanyToCompanyDTO(Company company) {
        return new CompanyDTO(
                company.getId(),
                company.getName(),
                company.getFooter(),
                company.getCreatedAt(),
                UserDTO.mapUserToUserDTO(company.getUser())
        );
    }

    public static List<CompanyDTO> mapCompanyListToCompanyDTOList(List<Company> companyList) {
        return companyList.stream().map(company -> new CompanyDTO(
                company.getId(),
                company.getName(),
                company.getFooter(),
                company.getCreatedAt(),
                UserDTO.mapUserToUserDTO(company.getUser())
        )).toList();
    }
}
