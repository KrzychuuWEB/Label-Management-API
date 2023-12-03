package pl.krzychuuweb.labelapp.security.ownership;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import pl.krzychuuweb.labelapp.auth.AuthQueryFacade;
import pl.krzychuuweb.labelapp.user.UserQueryFacade;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Aspect
@Component
class OwnershipAspect {

    private final UserQueryFacade userQueryFacade;

    private final AuthQueryFacade authQueryFacade;

    private final Map<Class<? extends OwnershipStrategy>, OwnershipStrategy> ownershipStrategyMap;

    OwnershipAspect(final UserQueryFacade userQueryFacade, final AuthQueryFacade authQueryFacade, List<OwnershipStrategy> ownershipStrategies) {
        this.userQueryFacade = userQueryFacade;
        this.authQueryFacade = authQueryFacade;
        this.ownershipStrategyMap = ownershipStrategies.stream()
                .collect(Collectors.toMap(OwnershipStrategy::getClass, Function.identity()));
    }

    @Around("@annotation(checkOwnership)")
    public Object checkOwnership(ProceedingJoinPoint proceedingJoinPoint, CheckOwnership checkOwnership) throws Throwable {
        Class<? extends OwnershipStrategy> strategyFromAnnotation = checkOwnership.value();
        OwnershipStrategy ownershipStrategy = ownershipStrategyMap.get(strategyFromAnnotation);

        Long userId = userQueryFacade.getUserByEmail(authQueryFacade.getLoggedUserEmail()).getId();
        Long resourceId = findResourceId(proceedingJoinPoint.getArgs());

        if (ownershipStrategy.isOwner(userId, resourceId)) {
            return proceedingJoinPoint.proceed();
        } else {
            throw new AccessDeniedException("You don't have access to this resource");
        }
    }

    private Long findResourceId(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof Long) {
                return (Long) arg;
            }
        }
        throw new IllegalArgumentException("Resource ID not found in method parameters");
    }
}
