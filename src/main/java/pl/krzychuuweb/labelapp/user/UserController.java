package pl.krzychuuweb.labelapp.user;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.krzychuuweb.labelapp.exceptions.BadRequestException;
import pl.krzychuuweb.labelapp.user.dto.UserCreateDTO;
import pl.krzychuuweb.labelapp.user.dto.UserDTO;
import pl.krzychuuweb.labelapp.user.dto.UserEditDTO;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/users")
class UserController {

    private final UserQueryFacade userQueryFacade;
    private final UserFacade userFacade;

    UserController(final UserQueryFacade userQueryFacade, final UserFacade userFacade) {
        this.userQueryFacade = userQueryFacade;
        this.userFacade = userFacade;
    }

    @GetMapping
    List<UserDTO> getAllUsers() {
        return UserDTO.mapUserListToUserDTOList(userQueryFacade.getAllUsers());
    }

    @GetMapping("/{id}")
    UserDTO getUserById(@PathVariable Long id) {
        return UserDTO.mapUserToUserDTO(userQueryFacade.getUserById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserDTO addUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        return UserDTO.mapUserToUserDTO(userFacade.addUser(userCreateDTO));
    }

    @PutMapping("/{id}")
    UserDTO updateUserById(@PathVariable Long id, @Valid @RequestBody UserEditDTO userEditDTO) {
        if (!Objects.equals(userEditDTO.id(), id)) {
            throw new BadRequestException("Id is not the same");
        }

        return UserDTO.mapUserToUserDTO(userFacade.updateUser(userEditDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUserById(@PathVariable Long id) {
        userFacade.deleteUserById(id);
    }
}