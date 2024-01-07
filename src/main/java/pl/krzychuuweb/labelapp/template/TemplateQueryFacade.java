package pl.krzychuuweb.labelapp.template;

import pl.krzychuuweb.labelapp.QueryFacade;

import java.util.List;

public interface TemplateQueryFacade extends QueryFacade {

    List<Template> getAllByLoggedUser();

    Template getById(final Long id);
}
