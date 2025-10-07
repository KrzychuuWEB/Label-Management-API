package pl.krzychuuweb.labelapp.template;

import org.springframework.stereotype.Repository;
import pl.krzychuuweb.labelapp.BaseQueryRepository;
import pl.krzychuuweb.labelapp.security.ownership.OwnershipRepository;

@Repository
interface TemplateQueryRepository extends BaseQueryRepository<Template>, OwnershipRepository<Template> {
}
