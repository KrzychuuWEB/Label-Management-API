package pl.krzychuuweb.labelapp.template;

import org.springframework.stereotype.Component;
import pl.krzychuuweb.labelapp.template.dto.TemplateCreateDTO;
import pl.krzychuuweb.labelapp.user.User;

@Component
class TemplateFactory {

    Template createTemplate(final TemplateCreateDTO templateCreateDTO, final User user) {
        return Template.TemplateBuilder.aTemplate()
                .withName(templateCreateDTO.name())
                .withWidth(templateCreateDTO.width())
                .withHeight(templateCreateDTO.height())
                .withUser(user)
                .build();
    }
}
