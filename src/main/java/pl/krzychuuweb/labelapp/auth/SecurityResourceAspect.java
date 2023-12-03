package pl.krzychuuweb.labelapp.auth;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Aspect
@Component
class SecurityResourceAspect {

    @Before("@annotation(CheckOwnership)")
    void checkOwnership(JoinPoint joinPoint) throws AccessDeniedException {
    }
}
