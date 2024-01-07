package pl.krzychuuweb.labelapp.template;

import pl.krzychuuweb.labelapp.template.dto.TemplateCreateDTO;
import pl.krzychuuweb.labelapp.template.dto.TemplateEditDTO;

interface TemplateFacade {

    Template create(final TemplateCreateDTO templateCreateDTO);

    Template edit(final TemplateEditDTO templateEditDTO);

    void deleteById(final Long id);
}
