package pl.krzychuuweb.labelapp.template;

import org.springframework.stereotype.Component;
import pl.krzychuuweb.labelapp.security.ownership.AbstractOwnershipStrategy;

@Component
class TemplateOwnershipStrategy extends AbstractOwnershipStrategy<Template> {

    private final TemplateQueryRepository templateQueryRepository;

    TemplateOwnershipStrategy(TemplateQueryRepository templateQueryRepository) {
        super(templateQueryRepository);
        this.templateQueryRepository = templateQueryRepository;
    }
}
