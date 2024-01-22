package pl.krzychuuweb.labelapp.nutritionalvalue.subnutritionalvalue;

import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.exception.NotFoundException;
import pl.krzychuuweb.labelapp.nutritionalvalue.AbstractPriorityQueryFacade;

import java.util.List;

@Service
class SubNutritionalValueQueryFacadeImpl extends AbstractPriorityQueryFacade<SubNutritionalValue> implements SubNutritionalValueQueryFacade {

    private final SubNutritionalValueQueryRepository subNutritionalValueQueryRepository;

    SubNutritionalValueQueryFacadeImpl(SubNutritionalValueQueryRepository subNutritionalValueQueryRepository) {
        super(subNutritionalValueQueryRepository);
        this.subNutritionalValueQueryRepository = subNutritionalValueQueryRepository;
    }

    @Override
    public SubNutritionalValue getById(final Long id) {
        return subNutritionalValueQueryRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found this sub nutritional values"));
    }

    @Override
    public List<SubNutritionalValue> getAll() {
        return subNutritionalValueQueryRepository.findAll();
    }

    @Override
    public List<SubNutritionalValue> getAllByListId(final List<Long> idList) {
        return subNutritionalValueQueryRepository.findAllById(idList);
    }
}
