package pl.krzychuuweb.labelapp.template.dto;

import pl.krzychuuweb.labelapp.template.Template;
import pl.krzychuuweb.labelapp.user.dto.UserDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record TemplateDTO(
        Long id,

        String name,

        BigDecimal width,

        BigDecimal height,

        UserDTO user,

        LocalDateTime createdAt
) {

    public static List<TemplateDTO> mapToTemplateDTOList(final List<Template> templates) {
        return templates.stream()
                .map(template -> new TemplateDTO(
                        template.getId(),
                        template.getName(),
                        template.getWidth(),
                        template.getHeight(),
                        UserDTO.mapUserToUserDTO(template.getUser()),
                        template.getCreatedAt()
                ))
                .toList();
    }

    public static TemplateDTO mapToTemplateDTO(final Template template) {
        return new TemplateDTO(
                template.getId(),
                template.getName(),
                template.getWidth(),
                template.getHeight(),
                UserDTO.mapUserToUserDTO(template.getUser()),
                template.getCreatedAt()
        );
    }
}
