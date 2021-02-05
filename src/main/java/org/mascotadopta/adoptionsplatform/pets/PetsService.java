package org.mascotadopta.adoptionsplatform.pets;

import org.mascotadopta.adoptionsplatform.pets.dto.PetDto;
import org.mascotadopta.adoptionsplatform.pets.dto.PetInfoDto;
import org.mascotadopta.adoptionsplatform.pets.dto.PostPetDto;
import org.mascotadopta.adoptionsplatform.users.User;
import org.mascotadopta.adoptionsplatform.users.UsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

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
     * Users repository.
     */
    private final UsersRepository usersRepository;
    
    /**
     * Single constructor.
     *
     * @param petsRepository  Pets repository.
     * @param usersRepository Users repository.
     */
    public PetsService(PetsRepository petsRepository,
                       UsersRepository usersRepository)
    {
        this.petsRepository = petsRepository;
        this.usersRepository = usersRepository;
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
     * @throws ResponseStatusException If the requested Pet does not exist (404 Not Found).
     */
    public PetDto getPetById(long id) throws ResponseStatusException
    {
        Optional<Pet> pet = this.petsRepository.findById(id);
        
        if (pet.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The requested Pet does not exist");
        
        return petToPetDto(pet.get());
    }
    
    /**
     * Deletes a Pet given its primary numerical key. The Pet must have been posted by the specified User.
     *
     * @param email Email of the poster.
     * @param id    ID of the Pet to delete.
     * @throws ResponseStatusException If the Pet to delete does not exist (404 Not Found) or it wasn't posted by the
     *                                 specified User (403 Forbidden).
     */
    public void deletePetById(String email, long id) throws ResponseStatusException
    {
        Pet pet = getPetPostedBy(email, id);
        this.petsRepository.delete(pet);
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
     * Posts a Pet for adoption
     *
     * @param email      Email of the poster.
     * @param postPetDto Information of the Pet being posted.
     * @throws ResponseStatusException If the User does not exist (404 Not Found).
     */
    public void postPet(String email, PostPetDto postPetDto) throws ResponseStatusException
    {
        Optional<User> optionalUser = this.usersRepository.findByEmail(email);
        
        if (optionalUser.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        
        Pet pet = new Pet(postPetDto, optionalUser.get());
        this.petsRepository.save(pet);
    }
    
    /**
     * Edits a Pet given its primary numerical key. The Pet must've been posted by the User associated with
     * <code>email</code>.
     *
     * @param email      Email of the User who posted the Pet.
     * @param postPetDto New information of the Pet.
     * @param id         ID of the Pet to edit.
     * @throws ResponseStatusException If the Pet does not exist (404 Not Found) or it wasn't posted by the specified
     *                                 User (403 Forbidden).
     */
    public void editPet(String email, PostPetDto postPetDto, long id) throws ResponseStatusException
    {
        Pet pet = getPetPostedBy(email, id);
        pet.updateFromDto(postPetDto);
        this.petsRepository.save(pet);
    }
    
    /**
     * Toggles the saved state in a Pet post.
     *
     * @param email Email of the User to modify the state from.
     * @param id    Pet ID.
     * @return The new saved state of the specified Pet.
     */
    public boolean togglePetSavedState(String email, @PathVariable long id)
    {
        Optional<Pet> optionalPet = this.petsRepository.findById(id);
        if (optionalPet.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        Pet pet = optionalPet.get();
        
        Optional<User> optionalUser = this.usersRepository.findByEmail(email);
        if (optionalUser.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        User user = optionalUser.get();
        
        Set<Pet> savedPets = user.getSavedPets();
        boolean saved = savedPets.contains(pet);
        
        if (saved) savedPets.remove(pet);
        else savedPets.add(pet);
        
        return !saved;
    }
    
    /**
     * Retrieves a Pet and checks if it was posted by the specified User.
     *
     * @param email Email of the poster.
     * @param id    ID of the Pet to retrieve.
     * @return The specified Pet.
     * @throws ResponseStatusException If the Pet does not exist (404 Not Found) or it wasn't posted by the specified
     *                                 User (403 Forbidden).
     */
    private Pet getPetPostedBy(String email, long id) throws ResponseStatusException
    {
        Optional<Pet> optionalPet = this.petsRepository.findById(id);
        if (optionalPet.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        Pet pet = optionalPet.get();
        
        if (!pet.getPostedBy().getEmail().equals(email)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        
        return pet;
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
