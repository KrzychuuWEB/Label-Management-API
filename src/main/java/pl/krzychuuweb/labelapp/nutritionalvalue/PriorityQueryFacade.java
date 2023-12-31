package pl.krzychuuweb.labelapp.nutritionalvalue;

import java.util.List;

public interface PriorityQueryFacade<T extends Priority> {

    void checkWhetherPriorityIsNotUsed(final Integer priority);

    List<T> getAll();

    List<T> getAllByPriorityBetweenRange(final Integer minPriority, final Integer maxPriority);

    T getById(final Long id);
}
