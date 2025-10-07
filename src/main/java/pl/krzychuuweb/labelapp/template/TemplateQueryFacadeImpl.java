package pl.krzychuuweb.labelapp.template;

import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.AbstractQueryFacade;
import pl.krzychuuweb.labelapp.auth.AuthQueryFacade;
import pl.krzychuuweb.labelapp.exception.NotFoundException;

import java.util.List;

@Service
class TemplateQueryFacadeImpl extends AbstractQueryFacade<Template> implements TemplateQueryFacade {

    private final TemplateQueryRepository templateQueryRepository;

    private final AuthQueryFacade authQueryFacade;

    TemplateQueryFacadeImpl(TemplateQueryRepository templateQueryRepository, AuthQueryFacade authQueryFacade) {
        super(templateQueryRepository, authQueryFacade);
        this.templateQueryRepository = templateQueryRepository;
        this.authQueryFacade = authQueryFacade;
    }

    @Override
    public List<Template> getAllByLoggedUser() {
        return templateQueryRepository.findAllByUser(authQueryFacade.getLoggedUser());
    }

    @Override
    public Template getById(final Long id) throws NotFoundException {
        return templateQueryRepository.findById(id).orElseThrow(() -> new NotFoundException("Template with this id is not found!"));
    }
}
