package pl.krzychuuweb.labelapp.nutritionalvalue;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.NutritionalValueCreateDTO;
import pl.krzychuuweb.labelapp.nutritionalvalue.dto.NutritionalValueEditDTO;

import java.math.BigDecimal;

@Service
class NutritionalValueFacadeImpl implements NutritionalValueFacade {

    private final NutritionalValueRepository nutritionalValueRepository;

    private final NutritionalValueQueryFacade nutritionalValueQueryFacade;

    private final PriorityQueryFacade priorityQueryFacade;

    private final NutritionalValueFactory nutritionalValueFactory;

    NutritionalValueFacadeImpl(
            final NutritionalValueRepository nutritionalValueRepository,
            final NutritionalValueQueryFacade nutritionalValueQueryFacade,
            final PriorityQueryFacade priorityQueryFacade,
            final NutritionalValueFactory nutritionalValueFactory
    ) {
        this.nutritionalValueRepository = nutritionalValueRepository;
        this.nutritionalValueQueryFacade = nutritionalValueQueryFacade;
        this.priorityQueryFacade = priorityQueryFacade;
        this.nutritionalValueFactory = nutritionalValueFactory;
    }

    @Override
    public NutritionalValue create(final NutritionalValueCreateDTO nutritionalValueCreateDTO) {
        return nutritionalValueRepository.save(
                nutritionalValueFactory.createNutritionalValue(nutritionalValueCreateDTO, generatePriority())
        );
    }

    @Override
    @Transactional
    public NutritionalValue edit(final NutritionalValueEditDTO nutritionalValueEditDTO, final Long id) {
        NutritionalValue nutritionalValue = nutritionalValueQueryFacade.getById(id);

        nutritionalValue.setName(nutritionalValueEditDTO.name());

        return nutritionalValueRepository.save(nutritionalValue);
    }

    @Override
    public void deleteById(final Long id) {
        NutritionalValue nutritionalValue = nutritionalValueQueryFacade.getById(id);

        
    }

    private BigDecimal generatePriority() {
        BigDecimal priority = priorityQueryFacade.getLastPriorityNumber();
        return priority.add(new BigDecimal("1.0"));
    }
}