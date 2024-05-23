package pl.krzychuuweb.labelapp.nutritionalvalue;

import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.exception.BadRequestException;

import java.util.List;

@Service
class NutritionalValueQueryFacadeImpl implements NutritionalValueQueryFacade {

    private final NutritionalValueQueryRepository nutritionalValueQueryRepository;

    NutritionalValueQueryFacadeImpl(final NutritionalValueQueryRepository nutritionalValueQueryRepository) {
        this.nutritionalValueQueryRepository = nutritionalValueQueryRepository;
    }

    @Override
    public NutritionalValue getById(final Long id) {
        return nutritionalValueQueryRepository.findById(id).orElseThrow(() -> new BadRequestException("Nutritional value with this id is not exists"));
    }

    @Override
    public List<NutritionalValue> getAll() {
        return nutritionalValueQueryRepository.findAll();
    }
}
