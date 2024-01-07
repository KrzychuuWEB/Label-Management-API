package pl.krzychuuweb.labelapp.template;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.krzychuuweb.labelapp.auth.AuthQueryFacade;
import pl.krzychuuweb.labelapp.exception.BadRequestException;
import pl.krzychuuweb.labelapp.template.dto.TemplateCreateDTO;
import pl.krzychuuweb.labelapp.template.dto.TemplateEditDTO;
import pl.krzychuuweb.labelapp.user.User;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TemplateFacadeImplTest {

    @Autowired
    private TemplateQueryFacade templateQueryFacade;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private AuthQueryFacade authQueryFacade;

    @Autowired
    private TemplateFactory templateFactory;

    private TemplateFacade templateFacade;


    @BeforeEach
    void setup() {
        templateQueryFacade = mock(TemplateQueryFacade.class);
        templateRepository = mock(TemplateRepository.class);
        authQueryFacade = mock(AuthQueryFacade.class);
        templateFactory = mock(TemplateFactory.class);
        templateFacade = new TemplateFacadeImpl(templateQueryFacade, templateRepository, authQueryFacade, templateFactory);
    }

    @Test
    void should_create_new_template() {
        User user = User.UserBuilder.anUser().withEmail("email@email.com").build();
        TemplateCreateDTO templateCreateDTO = new TemplateCreateDTO("name", new BigDecimal("125"), new BigDecimal("123"));
        Template template = Template.TemplateBuilder.aTemplate().withName(templateCreateDTO.name()).withHeight(templateCreateDTO.height()).withWidth(templateCreateDTO.width()).withUser(user).build();

        when(authQueryFacade.getLoggedUser()).thenReturn(user);
        when(templateQueryFacade.checkWhetherNameInResourceIsNotUsedByLoggedUser(anyString())).thenReturn(true);
        when(templateFactory.createTemplate(any(TemplateCreateDTO.class), any(User.class))).thenReturn(template);
        when(templateRepository.save(any(Template.class))).thenReturn(template);

        Template result = templateFacade.create(templateCreateDTO);

        assertThat(result).isInstanceOf(Template.class);
        assertThat(result.getName()).isEqualTo(templateCreateDTO.name());
        assertThat(result.getWidth()).isEqualTo(templateCreateDTO.width());
        assertThat(result.getHeight()).isEqualTo(templateCreateDTO.height());
        assertThat(result.getUser()).isInstanceOf(User.class);
        assertThat(result.getUser().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void should_create_new_template_return_exception_because_name_is_taken() {
        TemplateCreateDTO templateCreateDTO = new TemplateCreateDTO("name", new BigDecimal("125"), new BigDecimal("123"));

        when(templateQueryFacade.checkWhetherNameInResourceIsNotUsedByLoggedUser(anyString())).thenReturn(false);

        assertThatThrownBy(() -> templateFacade.create(templateCreateDTO)).isInstanceOf(BadRequestException.class);
    }


    @Test
    void should_edit_template() {
        TemplateEditDTO templateEditDTO = new TemplateEditDTO(1L, "newName", new BigDecimal("321"), new BigDecimal("321"));
        Template template = Template.TemplateBuilder.aTemplate().build();

        when(templateQueryFacade.checkWhetherNameInResourceIsNotUsedByLoggedUser(anyString())).thenReturn(true);
        when(templateQueryFacade.getById(anyLong())).thenReturn(template);
        when(templateRepository.save(any(Template.class))).thenReturn(template);

        Template result = templateFacade.edit(templateEditDTO);

        assertThat(result).isInstanceOf(Template.class);
        assertThat(result.getName()).isEqualTo(templateEditDTO.name());
        assertThat(result.getHeight()).isEqualTo(templateEditDTO.height());
        assertThat(result.getWidth()).isEqualTo(templateEditDTO.width());
    }

    @Test
    void should_edit_template_return_exception_because_name_is_taken() {
        TemplateEditDTO templateEditDTO = new TemplateEditDTO(1L, "name", new BigDecimal("125"), new BigDecimal("123"));

        when(templateQueryFacade.checkWhetherNameInResourceIsNotUsedByLoggedUser(anyString())).thenReturn(false);

        assertThatThrownBy(() -> templateFacade.edit(templateEditDTO)).isInstanceOf(BadRequestException.class);
    }

    @Test
    void should_delete_template_by_id() {
        Template template = Template.TemplateBuilder.aTemplate().build();

        when(templateQueryFacade.getById(anyLong())).thenReturn(template);

        templateFacade.deleteById(anyLong());

        verify(templateRepository, times(1)).delete(any());
    }
}