package pl.krzychuuweb.labelapp;

public interface QueryFacade {

    boolean checkWhetherNameInResourceIsNotUsedByLoggedUser(final String name);
}
