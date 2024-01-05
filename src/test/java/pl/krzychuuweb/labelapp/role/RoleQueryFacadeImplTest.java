package pl.krzychuuweb.labelapp.role;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.krzychuuweb.labelapp.exception.NotFoundException;
import pl.krzychuuweb.labelapp.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoleQueryFacadeImplTest {

    @Autowired
    private RoleQueryRepository roleQueryRepository;

    private RoleQueryFacade roleQueryFacade;

    @BeforeEach
    void setUp() {
        roleQueryRepository = mock(RoleQueryRepository.class);
        roleQueryFacade = new RoleQueryFacadeImpl(roleQueryRepository);
    }

    @Test
    void should_get_role_by_user_role() {
        UserRole userRole = UserRole.ROLE_USER;
        Role role = Role.RoleBuilder.aRole().withName(userRole).build();

        when(roleQueryRepository.getByName(userRole)).thenReturn(Optional.of(role));

        Role result = roleQueryFacade.getRoleByUserRole(userRole);

        assertThat(result.getName().getName()).isEqualTo("ROLE_USER");
    }

    @Test
    void should_get_role_by_user_role_expected_not_found_exception() {
        when(roleQueryRepository.getByName(any(UserRole.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            roleQueryFacade.getRoleByUserRole(UserRole.ROLE_USER);
        }).isInstanceOf(NotFoundException.class);
    }

    @Test
    void should_get_roles_list_for_user() {
        Role adminRole = Role.RoleBuilder.aRole().withName(UserRole.ROLE_ADMIN).build();
        Role userRole = Role.RoleBuilder.aRole().withName(UserRole.ROLE_USER).build();
        List<Role> roles = new ArrayList<>();
        roles.add(adminRole);
        roles.add(userRole);
        User user = User.UserBuilder.anUser().withId(1L).build();

        when(roleQueryRepository.getRolesByUserId(anyLong())).thenReturn(roles);

        List<Role> result = roleQueryFacade.getRolesForUser(user);

        assertThat(result).hasSameSizeAs(roles);
    }
}