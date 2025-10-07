package pl.krzychuuweb.labelapp.security;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JWTService {

    String extractUserEmail(final String token);

    String generateToken(final UserDetails userDetails);
    String generateToken(final Map<String, Object> claims, final UserDetails userDetails);

    boolean isTokenValid(final String token, final UserDetails userDetails);
}
