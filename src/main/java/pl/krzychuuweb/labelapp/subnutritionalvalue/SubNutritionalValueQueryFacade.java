package pl.krzychuuweb.labelapp.subnutritionalvalue;

public interface SubNutritionalValueQueryFacade {

    boolean checkWhetherPriorityIsNotUsed(final Integer priority);

    SubNutritionalValue getById(final Long id);
}
