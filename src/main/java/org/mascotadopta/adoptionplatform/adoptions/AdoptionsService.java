package org.mascotadopta.adoptionplatform.adoptions;

import org.mascotadopta.adoptionplatform.adoptions.dto.AdoptionApplicationDto;
import org.mascotadopta.adoptionplatform.adoptions.dto.AdoptionApplicationInfoDto;
import org.mascotadopta.adoptionplatform.adoptions.dto.PostAdoptionApplicationDto;
import org.mascotadopta.adoptionplatform.adoptions.survey.Survey;
import org.mascotadopta.adoptionplatform.adoptions.survey.SurveysRepository;
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
     * Survey repository.
     */
    private final SurveysRepository surveysRepository;
    
    /**
     * Single constructor.
     *
     * @param adoptionsRepository Adoptions repository.
     * @param usersRepository     Users repository.
     * @param petsRepository      Pets repository.
     * @param surveysRepository   Surveys repository.
     */
    public AdoptionsService(AdoptionsRepository adoptionsRepository,
                            UsersRepository usersRepository,
                            PetsRepository petsRepository,
                            SurveysRepository surveysRepository)
    {
        this.adoptionsRepository = adoptionsRepository;
        this.usersRepository = usersRepository;
        this.petsRepository = petsRepository;
        this.surveysRepository = surveysRepository;
    }
    
    /**
     * Retrieves the requested page of a User's adoption applications. Only a limited view of the data is returned
     * (<code>AdoptionApplicationInfoDto</code>).
     *
     * @param userAuthServerId External ID of the User to retrieve the applications from.
     * @param pageNumber       The requested page number.
     * @return The requested page of the User's applications.
     * @throws ResponseStatusException If the requested page does not exist (404 Not Found).
     */
    public Page<AdoptionApplicationInfoDto> getUserApplications(String userAuthServerId, int pageNumber) throws
            ResponseStatusException
    {
        Page<AdoptionApplication> adoptionApplications = this.adoptionsRepository
                .findAllByUserAuthServerId(userAuthServerId, PageRequest.of(pageNumber, ADOPTIONS_PAGE_SIZE));
    
        if (adoptionApplications.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The requested page does not exist");
    
        return adoptionApplications.map(AdoptionApplicationInfoDto::new);
    }
    
    /**
     * Posts an adoption application for a Pet.
     *
     * @param userAuthServerId           External applicant's ID.
     * @param postAdoptionApplicationDto Information needed to apply for the adoption of a Pet.
     */
    public void postApplication(String userAuthServerId, PostAdoptionApplicationDto postAdoptionApplicationDto) throws
            ResponseStatusException
    {
        Optional<User> optionalUser = this.usersRepository.findByAuthServerId(userAuthServerId);
        if (optionalUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    
        Optional<Pet> optionalPet = this.petsRepository.findById(postAdoptionApplicationDto.getPetId());
        if (optionalPet.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    
        // TODO: Add answers to Survey
        Survey survey = postAdoptionApplicationDto.getSurvey();
        survey = this.surveysRepository.save(survey);
    
        AdoptionApplication adoptionApplication = new AdoptionApplication(optionalUser.get(), optionalPet.get(),
                survey);
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
     * @param userAuthServerId External ID of the User who posted the application.
     * @param applicationId    ID of the application to retrieve.
     * @return The requested application.
     * @throws ResponseStatusException If the requested AdoptionApplication does not exist (404 Not Found) or it wasn't
     *                                 posted by the specified User (403 Forbidden).
     */
    public AdoptionApplicationDto getApplicationById(String userAuthServerId, long applicationId) throws
            ResponseStatusException
    {
        AdoptionApplication adoptionApplication = getApplicationPostedBy(userAuthServerId, applicationId);
        return new AdoptionApplicationDto(adoptionApplication);
    }
    
    /**
     * Deletes the specified application for a Pet.
     *
     * @param userAuthServerId External ID of the User who posted the application.
     * @param applicationId    ID of the application to delete.
     * @throws ResponseStatusException If the specified application does not exist (404 Not Found) or the applicant is
     *                                 not the specified User (403 Forbidden).
     */
    public void deleteApplicationById(String userAuthServerId, long applicationId) throws ResponseStatusException
    {
        AdoptionApplication adoptionApplication = getApplicationPostedBy(userAuthServerId, applicationId);
        this.adoptionsRepository.delete(adoptionApplication);
    }
    
    /**
     * Updates the status of the specified application.
     *
     * @param userAuthServerId External ID of the poster of the Pet for which the application is.
     * @param applicationId    ID of the application to update.
     * @param newStatus        New application status.
     * @throws ResponseStatusException If the specified application does not exist (404 Not Found) or the specified User
     *                                 is not the poster of the Pet for which the application is.
     */
    public void updateApplicationStatus(String userAuthServerId, long applicationId,
                                        AdoptionApplicationStatus newStatus)
    {
        Optional<AdoptionApplication> optionalAdoptionApplication = this.adoptionsRepository.findById(applicationId);
        if (optionalAdoptionApplication.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        AdoptionApplication adoptionApplication = optionalAdoptionApplication.get();
    
        User petPoster = adoptionApplication.getPet().getPostedBy();
        if (!petPoster.getAuthServerId().equals(userAuthServerId))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    
        adoptionApplication.setStatus(newStatus);
        this.adoptionsRepository.save(adoptionApplication);
    }
    
    /**
     * Retrieves an AdoptionApplication and checks if it was posted by the specified User.
     *
     * @param userAuthServerId External ID of the applicant.
     * @param applicationId    ID of the application to retrieve.
     * @return The specified application.
     * @throws ResponseStatusException If the specified application does not exist (404 Not Found) or the applicant is
     *                                 not the specified User (403 Forbidden).
     */
    private AdoptionApplication getApplicationPostedBy(String userAuthServerId, long applicationId) throws
            ResponseStatusException
    {
        Optional<AdoptionApplication> optionalAdoptionApplication = this.adoptionsRepository.findById(applicationId);
        if (optionalAdoptionApplication.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        AdoptionApplication adoptionApplication = optionalAdoptionApplication.get();
        
        if (!adoptionApplication.getUser().getAuthServerId().equals(userAuthServerId))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        return adoptionApplication;
    }
}
