package org.mascotadopta.adoptionplatform.adoptions;

import org.mascotadopta.adoptionplatform.adoptions.dto.AdoptionApplicationDto;
import org.mascotadopta.adoptionplatform.adoptions.dto.AdoptionApplicationInfoDto;
import org.mascotadopta.adoptionplatform.adoptions.dto.PostAdoptionApplicationDto;
import org.mascotadopta.adoptionplatform.pets.Pet;
import org.mascotadopta.adoptionplatform.pets.PetsRepository;
import org.mascotadopta.adoptionplatform.users.User;
import org.mascotadopta.adoptionplatform.users.UsersRepository;
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
     * @param authServerId External ID of the User to retrieve the applications from.
     * @param pageNumber   The requested page number.
     * @return The requested page of the User's applications.
     * @throws ResponseStatusException If the requested page does not exist (404 Not Found).
     */
    public Page<AdoptionApplicationInfoDto> getUserApplications(String authServerId, int pageNumber) throws
            ResponseStatusException
    {
        Page<AdoptionApplication> adoptionApplications = this.adoptionsRepository
                .findAllByUserAuthServerId(authServerId, PageRequest.of(pageNumber, ADOPTIONS_PAGE_SIZE));
    
        if (adoptionApplications.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The requested page does not exist");
    
        return adoptionApplications.map(AdoptionApplicationInfoDto::new);
    }
    
    /**
     * Posts an adoption application for a Pet.
     *
     * @param authServerId               External applicant's ID.
     * @param postAdoptionApplicationDto Information needed to apply for the adoption of a Pet.
     */
    public void postApplication(String authServerId, PostAdoptionApplicationDto postAdoptionApplicationDto) throws
            ResponseStatusException
    {
        Optional<User> optionalUser = this.usersRepository.findByAuthServerId(authServerId);
        if (optionalUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        
        Optional<Pet> optionalPet = this.petsRepository.findById(postAdoptionApplicationDto.getPetId());
        if (optionalPet.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        
        // TODO: Add answers to QuestionnaireResponses
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
     * @param authServerId  External ID of the User who posted the application.
     * @param applicationId ID of the application to retrieve.
     * @return The requested application.
     * @throws ResponseStatusException If the requested AdoptionApplication does not exist (404 Not Found) or it wasn't
     *                                 posted by the specified User (403 Forbidden).
     */
    public AdoptionApplicationDto getApplicationById(String authServerId, long applicationId) throws
            ResponseStatusException
    {
        AdoptionApplication adoptionApplication = getApplicationPostedBy(authServerId, applicationId);
        return new AdoptionApplicationDto(adoptionApplication);
    }
    
    /**
     * Deletes the specified application for a Pet.
     *
     * @param email Email of the User who posted the application.
     * @param id    ID of the application to delete.
     * @throws ResponseStatusException If the specified application does not exist (404 Not Found) or the applicant is
     *                                 not the specified User (403 Forbidden).
     */
    public void deleteApplicationById(String email, long id) throws ResponseStatusException
    {
        AdoptionApplication adoptionApplication = getApplicationPostedBy(email, id);
        this.adoptionsRepository.delete(adoptionApplication);
    }
    
    /**
     * Updates the status of the specified application.
     *
     * @param email     Email of the poster of the Pet for which the application is.
     * @param id        ID of the application to update.
     * @param newStatus New application status.
     * @throws ResponseStatusException If the specified application does not exist (404 Not Found) or the specified User
     *                                 is not the poster of the Pet for which the application is.
     */
    public void updateApplicationStatus(String email, long id, AdoptionApplicationStatus newStatus)
    {
        Optional<AdoptionApplication> optionalAdoptionApplication = this.adoptionsRepository.findById(id);
        if (optionalAdoptionApplication.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        AdoptionApplication adoptionApplication = optionalAdoptionApplication.get();
        
        User petPoster = adoptionApplication.getPet().getPostedBy();
        if (!petPoster.getEmail().equals(email)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        
        adoptionApplication.setStatus(newStatus);
        this.adoptionsRepository.save(adoptionApplication);
    }
    
    /**
     * Retrieves an AdoptionApplication and checks if it was posted by the specified User.
     *
     * @param authServerId External ID of the applicant.
     * @param applicationId    ID of the application to retrieve.
     * @return The specified application.
     * @throws ResponseStatusException If the specified application does not exist (404 Not Found) or the applicant is
     *                                 not the specified User (403 Forbidden).
     */
    private AdoptionApplication getApplicationPostedBy(String authServerId, long applicationId) throws
            ResponseStatusException
    {
        Optional<AdoptionApplication> optionalAdoptionApplication = this.adoptionsRepository.findById(applicationId);
        if (optionalAdoptionApplication.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        AdoptionApplication adoptionApplication = optionalAdoptionApplication.get();
    
        if (!adoptionApplication.getUser().getAuthServerId().equals(authServerId))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        return adoptionApplication;
    }
}
