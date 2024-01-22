package pl.krzychuuweb.labelapp.nutritionalvalue;

import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.exception.NotFoundException;

import java.util.List;

@Service
class NutritionalValueQueryFacadeImpl extends AbstractPriorityQueryFacade<NutritionalValue> implements NutritionalValueQueryFacade {

    private final NutritionalValueQueryRepository nutritionalValueQueryRepository;

    NutritionalValueQueryFacadeImpl(final NutritionalValueQueryRepository nutritionalValueQueryRepository) {
        super(nutritionalValueQueryRepository);
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
    public List<NutritionalValue> getAllByListId(final List<Long> idList) {
        List<NutritionalValue> listFromDB = nutritionalValueQueryRepository.findAllById(idList);

        if (idList.size() != listFromDB.size()) {
            throw new NotFoundException("Not found nutritional value");
        }

        return listFromDB;
    }
}
