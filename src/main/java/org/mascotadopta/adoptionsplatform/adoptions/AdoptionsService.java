package org.mascotadopta.adoptionsplatform.adoptions;

import org.mascotadopta.adoptionsplatform.adoptions.dto.AdoptionApplicationDto;
import org.mascotadopta.adoptionsplatform.adoptions.dto.AdoptionApplicationInfoDto;
import org.mascotadopta.adoptionsplatform.adoptions.dto.PostAdoptionApplicationDto;
import org.mascotadopta.adoptionsplatform.pets.Pet;
import org.mascotadopta.adoptionsplatform.pets.PetsRepository;
import org.mascotadopta.adoptionsplatform.users.User;
import org.mascotadopta.adoptionsplatform.users.UsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * Adoptions-related business logic.
 */
@Service
public class AdoptionsService
{
    /**
     * Page size of adoption applications.
     */
    private final static int ADOPTIONS_PAGE_SIZE = 12;
    
    /**
     * Adoptions repository.
     */
    private final AdoptionsRepository adoptionsRepository;
    
    /**
     * Users repository.
     */
    private final UsersRepository usersRepository;
    
    /**
     * Pets repository.
     */
    private final PetsRepository petsRepository;
    
    /**
     * QuestionnaireResponses repository.
     */
    private final QuestionnaireResponsesRepository questionnaireResponsesRepository;
    
    /**
     * Single constructor.
     *
     * @param adoptionsRepository              Adoptions repository.
     * @param usersRepository                  Users repository.
     * @param petsRepository                   Pets repository.
     * @param questionnaireResponsesRepository QuestionnaireResponses repository.
     */
    public AdoptionsService(AdoptionsRepository adoptionsRepository,
                            UsersRepository usersRepository,
                            PetsRepository petsRepository,
                            QuestionnaireResponsesRepository questionnaireResponsesRepository)
    {
        this.adoptionsRepository = adoptionsRepository;
        this.usersRepository = usersRepository;
        this.petsRepository = petsRepository;
        this.questionnaireResponsesRepository = questionnaireResponsesRepository;
    }
    
    /**
     * Retrieves the requested page of a User's adoption applications. Only a limited view of the data is returned
     * (<code>AdoptionApplicationInfoDto</code>).
     *
     * @param email      The email of the User to retrieve the applications from.
     * @param pageNumber The requested page number.
     * @return The requested page of the User's applications.
     * @throws ResponseStatusException If the requested page does not exist (404 Not Found).
     */
    public Page<AdoptionApplicationInfoDto> getUserApplications(String email, int pageNumber) throws
            ResponseStatusException
    {
        Page<AdoptionApplication> adoptionApplications = this.adoptionsRepository
                .findAllByUserEmail(email, PageRequest.of(pageNumber, ADOPTIONS_PAGE_SIZE));
    
        if (adoptionApplications.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The requested page does not exist");
    
        return adoptionApplications.map(AdoptionApplicationInfoDto::new);
    }
    
    /**
     * Posts an adoption application for a Pet.
     *
     * @param email                      Email of the applicant.
     * @param postAdoptionApplicationDto Information needed to apply for the adoption of a Pet.
     */
    public void postApplication(String email, PostAdoptionApplicationDto postAdoptionApplicationDto) throws
            ResponseStatusException
    {
        Optional<User> optionalUser = this.usersRepository.findByEmail(email);
        if (optionalUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    
        Optional<Pet> optionalPet = this.petsRepository.findById(postAdoptionApplicationDto.getPetId());
        if (optionalPet.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    
        QuestionnaireResponses questionnaireResponses = postAdoptionApplicationDto.getQuestionnaireResponses();
        questionnaireResponses = this.questionnaireResponsesRepository.save(questionnaireResponses);
    
        AdoptionApplication adoptionApplication = new AdoptionApplication(optionalUser.get(), optionalPet.get(),
                questionnaireResponses);
        this.adoptionsRepository.save(adoptionApplication);
    }
    
    /**
     * Retrieves the requested page of AdoptionApplications information.
     *
     * @param pageNumber Page number to retrieve.
     * @return The requested AdoptionApplication information Page.
     * @throws ResponseStatusException If the requested page does not exist (404 Not Found).
     */
    public Page<AdoptionApplicationInfoDto> getApplicationsPage(int pageNumber) throws ResponseStatusException
    {
        Page<AdoptionApplication> adoptionApplications = this.adoptionsRepository
                .findAll(PageRequest.of(pageNumber, ADOPTIONS_PAGE_SIZE));
    
        if (adoptionApplications.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    
        return adoptionApplications.map(AdoptionApplicationInfoDto::new);
    }
    
    /**
     * Retrieves a User's AdoptionApplication, given its primary numerical key.
     *
     * @param email Email of the User who posted the application.
     * @param id    ID of the application to retrieve.
     * @return The requested application.
     * @throws ResponseStatusException If the requested AdoptionApplication does not exist (404 Not Found) or it wasn't
     *                                 posted by the specified User (403 Forbidden).
     */
    public AdoptionApplicationDto getApplicationById(String email, long id) throws ResponseStatusException
    {
        Optional<AdoptionApplication> optionalAdoptionApplication = this.adoptionsRepository.findById(id);
        if (optionalAdoptionApplication.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        AdoptionApplication adoptionApplication = optionalAdoptionApplication.get();
        
        if (!adoptionApplication.getUser().getEmail().equals(email))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        
        return new AdoptionApplicationDto(adoptionApplication);
    }
}
