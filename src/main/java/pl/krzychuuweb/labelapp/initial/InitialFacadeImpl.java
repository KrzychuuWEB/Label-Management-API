package pl.krzychuuweb.labelapp.initial;

import org.springframework.stereotype.Service;
import pl.krzychuuweb.labelapp.auth.AuthQueryFacade;
import pl.krzychuuweb.labelapp.exception.BadRequestException;
import pl.krzychuuweb.labelapp.initial.dto.InitialCreateDTO;
import pl.krzychuuweb.labelapp.initial.dto.InitialEditDTO;
import pl.krzychuuweb.labelapp.user.User;

@Service
class InitialFacadeImpl implements InitialFacade {

    private final InitialRepository initialRepository;

    private final InitialQueryFacade initialQueryFacade;

    private final AuthQueryFacade authQueryFacade;

    private final InitialFactory initialFactory;

    InitialFacadeImpl(final InitialRepository initialRepository, final InitialQueryFacade initialQueryFacade, final AuthQueryFacade authQueryFacade, final InitialFactory initialFactory) {
        this.initialRepository = initialRepository;
        this.initialQueryFacade = initialQueryFacade;
        this.authQueryFacade = authQueryFacade;
        this.initialFactory = initialFactory;
    }

    @Override
    public Initial add(final InitialCreateDTO initialCreateDTO) throws BadRequestException {
        if (!initialQueryFacade.checkWhetherInitialNameIsNotUsed(initialCreateDTO.name())) {
            throw new BadRequestException("You not created new initial because this initial name is used!");
        }

        User user = authQueryFacade.getLoggedUser();

        return initialRepository.save(
                initialFactory.createInitialWithLoggedUser(initialCreateDTO, user)
        );
    }

    @Override
    public Initial edit(final InitialEditDTO initialEditDTO) throws BadRequestException {
        if (!initialQueryFacade.checkWhetherInitialNameIsNotUsed(initialEditDTO.name())) {
            throw new BadRequestException("You not edit old initial because new initial name is used!");
        }

        Initial initial = initialQueryFacade.getById(initialEditDTO.id());

        initial.setFirstName(initialEditDTO.firstName());
        initial.setLastName(initialEditDTO.lastName());
        initial.setName(initialEditDTO.name());

        return initialRepository.save(initial);
    }

    @Override
    public void deleteById(final Long id) {
        Initial initial = initialQueryFacade.getById(id);

        initialRepository.delete(initial);
    }
}
