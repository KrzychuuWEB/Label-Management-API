package pl.krzychuuweb.labelapp.role;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.krzychuuweb.labelapp.user.User;
import pl.krzychuuweb.labelapp.user.UserQueryFacade;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoleFacadeImplTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserQueryFacade userQueryFacade;

    @Autowired
    private RoleQueryFacade roleQueryFacade;

    private RoleFacade roleFacade;

    @BeforeEach
    void setUp() {
        roleRepository = mock(RoleRepository.class);
        userQueryFacade = mock(UserQueryFacade.class);
        roleQueryFacade = mock(RoleQueryFacade.class);
        roleFacade = new RoleFacadeImpl(roleRepository, userQueryFacade, roleQueryFacade);
    }

    @Test
    void should_assign_role_for_user() {
        UserRole roleToAdd = UserRole.ROLE_ADMIN;
        User user = User.UserBuilder.anUser().withId(1L).build();
        Role role = Role.RoleBuilder.aRole().withName(roleToAdd).build();

        when(userQueryFacade.getUserById(user.getId())).thenReturn(user);
        when(roleQueryFacade.getRoleByUserRole(roleToAdd)).thenReturn(role);
        when(roleQueryFacade.getRolesForUser(any(User.class))).thenReturn(List.of(role));

        List<Role> result = roleFacade.assignRole(user.getId(), roleToAdd);

        assertThat(result).hasSize(1);
        assertThat(result.stream().findFirst().get().getName().getName()).isEqualTo(roleToAdd.getName());
    }
}