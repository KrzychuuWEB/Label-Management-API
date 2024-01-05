package pl.krzychuuweb.labelapp.nutritionalvalue;

import jakarta.transaction.Transactional;
import pl.krzychuuweb.labelapp.exception.BadRequestException;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.ChangeNutritionalValuePriorityDTO;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractPriorityFacade<T extends Priority> {

    private final PriorityRepository<T> priorityRepository;

    private final PriorityQueryFacade<T> priorityQueryFacade;

    protected AbstractPriorityFacade(PriorityRepository<T> priorityRepository, PriorityQueryFacade<T> priorityQueryFacade) {
        this.priorityRepository = priorityRepository;
        this.priorityQueryFacade = priorityQueryFacade;
    }

    @Transactional
    public List<T> editPriority(ChangeNutritionalValuePriorityDTO changeNutritionalValuePriorityDTO) {
        T nutritionalValue = priorityQueryFacade.getById(changeNutritionalValuePriorityDTO.id());

        if (nutritionalValue.getPriority().equals(changeNutritionalValuePriorityDTO.priority())) {
            throw new BadRequestException("The priority has the same number");
        }

        List<T> nutritionalValueList = getAllNutritionalValuesByPriorityBetween(changeNutritionalValuePriorityDTO.priority(), nutritionalValue.getPriority());
        nutritionalValueList.stream()
                .sorted(Comparator.comparing(Priority::getPriority))
                .forEach(nv -> {
                    if (nv.getId().equals(nutritionalValue.getId())) {
                        nv.setPriority(changeNutritionalValuePriorityDTO.priority());
                    } else {
                        nv.setPriority(
                                nv.getPriority() + ((changeNutritionalValuePriorityDTO.priority() < nutritionalValue.getPriority()) ? 1 : -1)
                        );
                    }
                });

        priorityRepository.saveAll(nutritionalValueList);

        return priorityQueryFacade.getAll();
    }

    private List<T> getAllNutritionalValuesByPriorityBetween(Integer newPriority, Integer actualPriority) {
        return priorityQueryFacade.getAllByPriorityBetweenRange(
                Math.min(newPriority, actualPriority),
                Math.max(newPriority, actualPriority)
        );
    }
}
