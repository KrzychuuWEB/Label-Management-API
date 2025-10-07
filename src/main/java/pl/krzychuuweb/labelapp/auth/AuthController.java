package pl.krzychuuweb.labelapp.auth;

import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.krzychuuweb.labelapp.auth.dto.AuthDTO;
import pl.krzychuuweb.labelapp.auth.dto.LoginDTO;
import pl.krzychuuweb.labelapp.security.JWTService;
import pl.krzychuuweb.labelapp.user.User;
import pl.krzychuuweb.labelapp.user.UserFacade;
import pl.krzychuuweb.labelapp.user.UserQueryFacade;
import pl.krzychuuweb.labelapp.user.dto.UserCreateDTO;

@RestController
@RequestMapping("/auth")
class AuthController {

    private final UserFacade userFacade;

    private final UserQueryFacade userQueryFacade;

    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;

    AuthController(final UserFacade userFacade, final UserQueryFacade userQueryFacade, final JWTService jwtService, final AuthenticationManager authenticationManager) {
        this.userFacade = userFacade;
        this.userQueryFacade = userQueryFacade;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    AuthDTO authenticate(@Valid @RequestBody LoginDTO loginDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password())
        );

        User user = userQueryFacade.getUserByEmail(loginDTO.email());

        String jwt = jwtService.generateToken(user);

        return new AuthDTO(jwt);
    }

    @PostMapping("/register")
    AuthDTO register(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        User user = userFacade.addUser(userCreateDTO);
        String jwt = jwtService.generateToken(user);

        return new AuthDTO(jwt);
    }
}
