package org.mascotadopta.adoptionsplatform.pets;

import org.mascotadopta.adoptionsplatform.pets.dto.PetInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Pets-related business logic.
 */
@Service
public class PetsService
{
    /**
     * Page size of pet posts.
     */
    private final static int PETS_PAGE_SIZE = 12;
    
    /**
     * Pets repository.
     */
    private final PetsRepository petsRepository;
    
    /**
     * Single constructor.
     *
     * @param petsRepository Pets repository.
     */
    public PetsService(PetsRepository petsRepository)
    {
        this.petsRepository = petsRepository;
    }
    
    /**
     * Retrieves the requested page of a User's pet posts. Only a limited view of the data is returned
     * (<code>PetInfoDto</code>).
     *
     * @param email      The email of the User to retrieve the pets posts from.
     * @param pageNumber The requested page number.
     * @return The requested page of the User's pet posts.
     * @throws ResponseStatusException If the requested page does not exist (404 Not Found).
     */
    public Page<PetInfoDto> getUserPosts(String email, int pageNumber) throws ResponseStatusException
    {
        Page<Pet> pets = this.petsRepository
                .findAllByPostedByEmail(email, PageRequest.of(pageNumber, PETS_PAGE_SIZE));
        
        if (pets.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The requested page does not exist");
        
        return pets.map(PetInfoDto::new);
    }
}
