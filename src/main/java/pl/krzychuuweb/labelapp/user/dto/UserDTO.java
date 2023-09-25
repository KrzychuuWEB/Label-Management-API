package pl.krzychuuweb.labelapp.user.dto;

import pl.krzychuuweb.labelapp.user.User;

import java.time.LocalDateTime;
import java.util.List;

public record UserDTO(
        Long id,
        String username,
        String email,
        LocalDateTime createdAt
) {
    public static UserDTO mapUserToUserDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt());
    }

    public static List<UserDTO> mapUserListToUserDTOList(List<User> users) {
        return users.stream().map(
                user -> new UserDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getCreatedAt())
        ).toList();
    }
}
