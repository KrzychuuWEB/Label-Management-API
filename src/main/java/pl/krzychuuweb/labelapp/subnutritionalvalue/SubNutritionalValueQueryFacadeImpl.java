package pl.krzychuuweb.labelapp.subnutritionalvalue;

import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.exceptions.BadRequestException;
import pl.krzychuuweb.labelapp.exceptions.NotFoundException;

@Service
class SubNutritionalValueQueryFacadeImpl implements SubNutritionalValueQueryFacade {

    private final SubNutritionalValueQueryRepository subNutritionalValueQueryRepository;

    SubNutritionalValueQueryFacadeImpl(SubNutritionalValueQueryRepository subNutritionalValueQueryRepository) {
        this.subNutritionalValueQueryRepository = subNutritionalValueQueryRepository;
    }

    @Override
    public SubNutritionalValue getById(final Long id) {
        return subNutritionalValueQueryRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found this sub nutritional values"));
    }

    @Override
    public boolean checkWhetherPriorityIsNotUsed(final Integer priority) {
        if (subNutritionalValueQueryRepository.existsByPriority(priority)) {
            throw new BadRequestException("This priority has been used");
        }

        return true;
    }
}
