package pl.krzychuuweb.labelapp.nutritionalvalue;

import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.exceptions.BadRequestException;
import pl.krzychuuweb.labelapp.exceptions.NotFoundException;

import java.util.List;

@Service
class NutritionalValueQueryFacadeImpl implements NutritionalValueQueryFacade {

    private final NutritionalValueQueryRepository nutritionalValueQueryRepository;

    NutritionalValueQueryFacadeImpl(final NutritionalValueQueryRepository nutritionalValueQueryRepository) {
        this.nutritionalValueQueryRepository = nutritionalValueQueryRepository;
    }

    @Override
    public List<NutritionalValue> getAll() {
        return nutritionalValueQueryRepository.findAll();
    }

    @Override
    public NutritionalValue getById(final Long id) {
        return nutritionalValueQueryRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found nutritional values"));
    }

    @Override
    public boolean checkWhetherPriorityIsNotUsed(final Integer priority) {
        if (nutritionalValueQueryRepository.existsByPriority(priority)) {
            throw new BadRequestException("This priority has been used");
        }

        return true;
    }
}
