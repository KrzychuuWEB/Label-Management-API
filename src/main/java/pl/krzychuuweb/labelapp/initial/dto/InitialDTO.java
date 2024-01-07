package pl.krzychuuweb.labelapp.initial.dto;

import pl.krzychuuweb.labelapp.initial.Initial;
import pl.krzychuuweb.labelapp.user.dto.UserDTO;

import java.util.List;

public record InitialDTO(
        Long id,

        String firstName,

        String lastName,

        String name,

        UserDTO user
) {
    public static List<InitialDTO> mapInitialsToInitialsDTO(List<Initial> initials) {
        return initials.stream().map(initial -> new InitialDTO(
                initial.getId(),
                initial.getFirstName(),
                initial.getLastName(),
                initial.getName(),
                UserDTO.mapUserToUserDTO(initial.getUser())
        )).toList();
    }

    public static InitialDTO mapInitialToInitialDTO(Initial initial) {
        return new InitialDTO(
                initial.getId(),
                initial.getFirstName(),
                initial.getLastName(),
                initial.getName(),
                UserDTO.mapUserToUserDTO(initial.getUser())
        );
    }
}
