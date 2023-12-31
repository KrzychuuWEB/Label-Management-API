package pl.krzychuuweb.labelapp.nutritionalvalue;

import pl.krzychuuweb.labelapp.exceptions.BadRequestException;

import java.util.List;

public abstract class AbstractPriorityQueryFacade<T extends Priority> {

    private final PriorityQueryRepository<T> priorityQueryRepository;

    protected AbstractPriorityQueryFacade(PriorityQueryRepository<T> priorityQueryRepository) {
        this.priorityQueryRepository = priorityQueryRepository;
    }

    public void checkWhetherPriorityIsNotUsed(final Integer priority) {
        if (priorityQueryRepository.existsByPriority(priority)) {
            throw new BadRequestException("This priority has been used");
        }
    }

    public List<T> getAllByPriorityBetweenRange(final Integer minPriority, final Integer maxPriority) {
        return priorityQueryRepository.findAllByPriorityBetween(minPriority, maxPriority);
    }
}
