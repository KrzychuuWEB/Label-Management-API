package pl.krzychuuweb.labelapp.initial;

import pl.krzychuuweb.labelapp.initial.dto.InitialCreateDTO;
import pl.krzychuuweb.labelapp.initial.dto.InitialEditDTO;

public interface InitialFacade {

    Initial add(final InitialCreateDTO initialCreateDTO);

    Initial edit(final InitialEditDTO initialEditDTO);

    void deleteById(final Long id);
}
