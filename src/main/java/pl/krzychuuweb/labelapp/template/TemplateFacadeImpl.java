package pl.krzychuuweb.labelapp.template;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.auth.AuthQueryFacade;
import pl.krzychuuweb.labelapp.exception.BadRequestException;
import pl.krzychuuweb.labelapp.template.dto.TemplateCreateDTO;
import pl.krzychuuweb.labelapp.template.dto.TemplateEditDTO;

@Service
class TemplateFacadeImpl implements TemplateFacade {

    private final TemplateQueryFacade templateQueryFacade;

    private final TemplateRepository templateRepository;

    private final AuthQueryFacade authQueryFacade;

    private final TemplateFactory templateFactory;

    TemplateFacadeImpl(TemplateQueryFacade templateQueryFacade, TemplateRepository templateRepository, AuthQueryFacade authQueryFacade, TemplateFactory templateFactory) {
        this.templateQueryFacade = templateQueryFacade;
        this.templateRepository = templateRepository;
        this.authQueryFacade = authQueryFacade;
        this.templateFactory = templateFactory;
    }

    @Override
    @Transactional
    public Template create(final TemplateCreateDTO templateCreateDTO) {
        if (!templateQueryFacade.checkWhetherNameInResourceIsNotUsedByLoggedUser(templateCreateDTO.name())) {
            throw new BadRequestException("You will not create new template because name is taken!");
        }

        return templateRepository.save(
                templateFactory.createTemplate(templateCreateDTO, authQueryFacade.getLoggedUser())
        );
    }

    @Override
    @Transactional
    public Template edit(final TemplateEditDTO templateEditDTO) {
        if (!templateQueryFacade.checkWhetherNameInResourceIsNotUsedByLoggedUser(templateEditDTO.name())) {
            throw new BadRequestException("You will not edit template because name is taken!");
        }

        Template template = templateQueryFacade.getById(templateEditDTO.id());

        template.setName(templateEditDTO.name());
        template.setWidth(templateEditDTO.width());
        template.setHeight(templateEditDTO.height());

        return templateRepository.save(template);
    }

    @Override
    @Transactional
    public void deleteById(final Long id) {
        Template template = templateQueryFacade.getById(id);

        templateRepository.delete(template);
    }
}
