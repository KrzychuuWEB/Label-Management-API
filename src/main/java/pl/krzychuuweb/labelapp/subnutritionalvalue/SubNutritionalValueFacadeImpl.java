package pl.krzychuuweb.labelapp.subnutritionalvalue;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValue;
import pl.krzychuuweb.labelapp.nutritionalvalue.NutritionalValueQueryFacade;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.CreateNutritionalValueDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.EditNutritionalValueDTO;

@Service
class SubNutritionalValueFacadeImpl implements SubNutritionalValueFacade {

    private final SubNutritionalValueRepository subNutritionalValueRepository;

    private final SubNutritionalValueFactory subNutritionalValueFactory;

    private final NutritionalValueQueryFacade nutritionalValueQueryFacade;

    private final SubNutritionalValueQueryFacade subNutritionalValueQueryFacade;

    SubNutritionalValueFacadeImpl(
            SubNutritionalValueRepository subNutritionalValueRepository,
            SubNutritionalValueFactory subNutritionalValueFactory,
            NutritionalValueQueryFacade nutritionalValueQueryFacade,
            SubNutritionalValueQueryFacade subNutritionalValueQueryFacade
    ) {
        this.subNutritionalValueRepository = subNutritionalValueRepository;
        this.subNutritionalValueFactory = subNutritionalValueFactory;
        this.nutritionalValueQueryFacade = nutritionalValueQueryFacade;
        this.subNutritionalValueQueryFacade = subNutritionalValueQueryFacade;
    }

    @Override
    public SubNutritionalValue add(final CreateNutritionalValueDTO createNutritionalValueDTO, Long nutritionalId) {
        subNutritionalValueQueryFacade.checkWhetherPriorityIsNotUsed(createNutritionalValueDTO.priority());
        NutritionalValue nutritionalValue = nutritionalValueQueryFacade.getById(nutritionalId);

        return subNutritionalValueRepository.save(
                subNutritionalValueFactory.createSubNutritionalValue(createNutritionalValueDTO, nutritionalValue)
        );
    }

    @Override
    @Transactional
    public SubNutritionalValue edit(final EditNutritionalValueDTO editNutritionalValueDTO) {
        SubNutritionalValue subNutritionalValue = subNutritionalValueQueryFacade.getById(editNutritionalValueDTO.id());
        subNutritionalValue.setName(editNutritionalValueDTO.name());

        return subNutritionalValueRepository.save(subNutritionalValue);
    }

    @Override
    @Transactional
    public void delete(final Long id) {
        SubNutritionalValue subNutritionalValue = subNutritionalValueQueryFacade.getById(id);

        subNutritionalValueRepository.delete(subNutritionalValue);
    }
}
