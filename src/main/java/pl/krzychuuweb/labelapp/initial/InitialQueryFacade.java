package pl.krzychuuweb.labelapp.initial;

import java.util.List;

public interface InitialQueryFacade {

    Initial getById(final Long id);

    List<Initial> getAllByLoggedUser();

    boolean checkWhetherInitialNameIsNotUsed(final String name);
}
