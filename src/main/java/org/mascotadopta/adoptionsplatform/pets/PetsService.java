package org.mascotadopta.adoptionsplatform.pets;

import org.mascotadopta.adoptionsplatform.pets.dto.PetDto;
import org.mascotadopta.adoptionsplatform.pets.dto.PetInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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
     * Retrieves the requested page of Pet information.
     *
     * @param pageNumber Page number to retrieve.
     * @return The requested Pet information <code>Page</code>.
     * @throws ResponseStatusException If the requested page does not exist (404 Not Found).
     */
    public Page<PetInfoDto> getPetsPage(int pageNumber) throws ResponseStatusException
    {
        Page<Pet> pets = this.petsRepository.findAll(PageRequest.of(pageNumber, PETS_PAGE_SIZE));
        
        if (pets.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The requested page does not exist");
        
        return pets.map(PetInfoDto::new);
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
    public Page<PetInfoDto> getUserPets(String email, int pageNumber) throws ResponseStatusException
    {
        Page<Pet> pets = this.petsRepository
                .findAllByPostedByEmail(email, PageRequest.of(pageNumber, PETS_PAGE_SIZE));
    
        if (pets.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The requested page does not exist");
    
        return pets.map(PetInfoDto::new);
    }
    
    /**
     * Retrieves a Pet given its primary numerical key.
     *
     * @param id ID of the Pet to retrieve.
     * @return The requested Pet.
     */
    public PetDto getPetById(long id)
    {
        Optional<Pet> pet = this.petsRepository.findById(id);
    
        if (pet.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The requested Pet does not exist");
    
        return petToPetDto(pet.get());
    }
    
    /**
     * Retrieves the requested page of a User's saved pet posts. Only a limited view of the data is returned
     * (<code>PetInfoDto</code>).
     *
     * @param email      Email of the User to retrieve the saved pet posts from.
     * @param pageNumber Page number to retrieve.
     * @return The requested Page of the User's saved pet posts.
     * @throws ResponseStatusException If the requested page does not exist (404 Not Found).
     */
    public Page<PetInfoDto> getSavedPets(String email, int pageNumber) throws ResponseStatusException
    {
        Page<Pet> pets = this.petsRepository
                .findAllBySavedByEmail(email, PageRequest.of(pageNumber, PETS_PAGE_SIZE));
        
        if (pets.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The requested page does not exist");
        
        return pets.map(PetInfoDto::new);
    }
    
    /**
     * Maps a <code>Pet</code> to a PetDto, obtaining necessary data.
     *
     * @param pet Pet to map.
     * @return A <code>PetDto</code> containing all related information to the given Pet.
     */
    private PetDto petToPetDto(Pet pet)
    {
        long numberOfApplications = this.petsRepository.countApplicationsById(pet.getId());
        return new PetDto(pet, numberOfApplications);
    }
    
}
