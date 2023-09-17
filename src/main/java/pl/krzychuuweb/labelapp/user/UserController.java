package pl.krzychuuweb.labelapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    private final UserQueryFacade userQueryFacade;

    UserController(final UserQueryFacade userQueryFacade) {
        this.userQueryFacade = userQueryFacade;
    }

    @GetMapping
    List<User> getAllUsers() {
        return userQueryFacade.getAll();
    }

    @GetMapping("/{email}")
    User getUserByEmail(@PathVariable String email) {
        return userQueryFacade.getUserByEmail(email);
    }
}
