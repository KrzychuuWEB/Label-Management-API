package pl.krzychuuweb.labelapp.template;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.krzychuuweb.labelapp.auth.AuthQueryFacade;
import pl.krzychuuweb.labelapp.exception.NotFoundException;
import pl.krzychuuweb.labelapp.user.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TemplateQueryFacadeImplTest {

    @Autowired
    private TemplateQueryRepository templateQueryRepository;

    @Autowired
    private AuthQueryFacade authQueryFacade;

    private TemplateQueryFacade templateQueryFacade;

    @BeforeEach
    void setup() {
        templateQueryRepository = mock(TemplateQueryRepository.class);
        authQueryFacade = mock(AuthQueryFacade.class);
        templateQueryFacade = new TemplateQueryFacadeImpl(templateQueryRepository, authQueryFacade);
    }

    @Test
    void should_get_all_templates_for_logged_user() {
        User user = User.UserBuilder.anUser().withEmail("email@email.com").build();

        List<Template> templates = List.of(
                Template.TemplateBuilder.aTemplate().withUser(user).build(),
                Template.TemplateBuilder.aTemplate().withUser(user).build(),
                Template.TemplateBuilder.aTemplate().withUser(user).build(),
                Template.TemplateBuilder.aTemplate().withUser(user).build()
        );

        when(authQueryFacade.getLoggedUser()).thenReturn(user);
        when(templateQueryRepository.findAllByUser(any(User.class))).thenReturn(templates);

        List<Template> result = templateQueryFacade.getAllByLoggedUser();

        assertThat(result).hasSameSizeAs(templates);
    }

    @Test
    void should_return_whether_name_is_not_used() {
        User user = User.UserBuilder.anUser().withEmail("email@email.com").build();
        when(authQueryFacade.getLoggedUser()).thenReturn(user);
        when(templateQueryRepository.existsByNameAndUser(any(), any(User.class))).thenReturn(false);

        boolean result = templateQueryFacade.checkWhetherNameInResourceIsNotUsedByLoggedUser("test");

        assertThat(result).isTrue();
    }

    @Test
    void should_return_whether_name_is_used() {
        User user = User.UserBuilder.anUser().withEmail("email@email.com").build();
        when(authQueryFacade.getLoggedUser()).thenReturn(user);
        when(templateQueryRepository.existsByNameAndUser(any(), any(User.class))).thenReturn(true);

        boolean result = templateQueryFacade.checkWhetherNameInResourceIsNotUsedByLoggedUser("test");

        assertThat(result).isFalse();
    }

    @Test
    void should_return_template_by_id() {
        User user = User.UserBuilder.anUser().build();
        Template template = Template.TemplateBuilder.aTemplate()
                .withName("name")
                .withUser(user)
                .withWidth(new BigDecimal("123.2"))
                .withHeight(new BigDecimal("123.2"))
                .build();

        when(templateQueryRepository.findById(anyLong())).thenReturn(Optional.of(template));

        Template result = templateQueryFacade.getById(anyLong());

        assertThat(result.getName()).isEqualTo(template.getName());
        assertThat(result.getHeight()).isEqualTo(template.getHeight());
        assertThat(result.getWidth()).isEqualTo(template.getWidth());
        assertThat(result.getUser()).isInstanceOf(User.class);
        assertThat(result.getUser().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void should_method_get_by_id_return_not_found_exception() {
        when(templateQueryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> templateQueryFacade.getById(anyLong())).isInstanceOf(NotFoundException.class);
    }
}