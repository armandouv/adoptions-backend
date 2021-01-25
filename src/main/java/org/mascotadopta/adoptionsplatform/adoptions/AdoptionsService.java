package org.mascotadopta.adoptionsplatform.adoptions;

import org.mascotadopta.adoptionsplatform.adoptions.dto.AdoptionApplicationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
     * Single constructor.
     *
     * @param adoptionsRepository Adoptions repository.
     */
    public AdoptionsService(AdoptionsRepository adoptionsRepository)
    {
        this.adoptionsRepository = adoptionsRepository;
    }
    
    /**
     * Retrieves the requested page of a User's adoption applications.
     *
     * @param email      The email of the User to retrieve the applications from.
     * @param pageNumber The requested page number.
     * @return The requested page of the User's applications.
     * @throws ResponseStatusException If the requested page does not exist (404 Not Found).
     */
    public Page<AdoptionApplicationDto> getApplications(String email, int pageNumber) throws ResponseStatusException
    {
        Page<AdoptionApplication> adoptionApplications = this.adoptionsRepository
                .findAllByUserEmail(email, PageRequest.of(pageNumber, ADOPTIONS_PAGE_SIZE));
        
        if (adoptionApplications.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The requested page does not exist");
        
        return adoptionApplications.map(AdoptionApplicationDto::new);
    }
}
