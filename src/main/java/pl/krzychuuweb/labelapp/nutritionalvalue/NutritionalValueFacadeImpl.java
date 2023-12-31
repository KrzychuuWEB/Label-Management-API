package pl.krzychuuweb.labelapp.nutritionalvalue;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.CreateNutritionalValueDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.EditNutritionalValueDTO;

@Service
class NutritionalValueFacadeImpl extends AbstractPriorityFacade<NutritionalValue> implements NutritionalValueFacade {

    private final NutritionalValueRepository nutritionalValueRepository;

    private final NutritionalValueQueryFacade nutritionalValueQueryFacade;

    private final NutritionalValueFactory nutritionalValueFactory;

    NutritionalValueFacadeImpl(final NutritionalValueRepository nutritionalValueRepository, NutritionalValueQueryFacade nutritionalValueQueryFacade, NutritionalValueFactory nutritionalValueFactory) {
        super(nutritionalValueRepository, nutritionalValueQueryFacade);
        this.nutritionalValueRepository = nutritionalValueRepository;
        this.nutritionalValueQueryFacade = nutritionalValueQueryFacade;
        this.nutritionalValueFactory = nutritionalValueFactory;
    }

    @Override
    public NutritionalValue add(final CreateNutritionalValueDTO createNutritionalValueDTO) {
        nutritionalValueQueryFacade.checkWhetherPriorityIsNotUsed(createNutritionalValueDTO.priority());

        return nutritionalValueRepository.save(nutritionalValueFactory.createNutritionalValue(createNutritionalValueDTO));
    }

    @Override
    @Transactional
    public NutritionalValue edit(final EditNutritionalValueDTO editNutritionalValueDTO) {
        NutritionalValue nutritionalValue = nutritionalValueQueryFacade.getById(editNutritionalValueDTO.id());

        nutritionalValue.setName(editNutritionalValueDTO.name());

        return nutritionalValueRepository.save(nutritionalValue);
    }

    @Override
    @Transactional
    public void delete(final Long id) {
        NutritionalValue nutritionalValue = nutritionalValueQueryFacade.getById(id);

        nutritionalValueRepository.delete(nutritionalValue);
    }
}
