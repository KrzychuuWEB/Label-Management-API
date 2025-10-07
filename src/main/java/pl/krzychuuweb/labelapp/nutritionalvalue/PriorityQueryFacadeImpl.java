package pl.krzychuuweb.labelapp.nutritionalvalue;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Service
class PriorityQueryFacadeImpl implements PriorityQueryFacade {

    private final NutritionalValueQueryRepository nutritionalValueQueryRepository;

    PriorityQueryFacadeImpl(final NutritionalValueQueryRepository nutritionalValueQueryRepository) {
        this.nutritionalValueQueryRepository = nutritionalValueQueryRepository;
    }

    @Override
    public BigDecimal getLastPriorityNumber() {
        List<BigDecimal> priorities = nutritionalValueQueryRepository.findAllOrderByPriorityDesc();

        return priorities.stream()
                .filter(priority -> priority.compareTo(BigDecimal.ZERO) > 0 && priority.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0)
                .max(Comparator.naturalOrder())
                .orElse(new BigDecimal("0.0"));
    }
}
