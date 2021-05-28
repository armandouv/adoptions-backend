package org.mascotadopta.adoptionplatform.pets;

import org.mascotadopta.adoptionplatform.pets.dto.PetDto;
import org.mascotadopta.adoptionplatform.pets.dto.PetInfoDto;
import org.mascotadopta.adoptionplatform.pets.dto.PostPetDto;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

/**
 * Pets-related routes.
 */
@RequestMapping("pets")
@RestController
public class PetsController
{
    /**
     * Pets service.
     */
    private final PetsService petsService;
    
    /**
     * Single constructor.
     *
     * @param petsService Pets service.
     */
    public PetsController(PetsService petsService)
    {
        this.petsService = petsService;
    }
    
    /**
     * Retrieves the requested page of Pet information.
     *
     * @param pageNumber Page number to retrieve.
     * @return The requested Pet information <code>Page</code>.
     * @throws ResponseStatusException If the requested page does not exist (404 Not Found).
     */
    @GetMapping
    public Page<PetInfoDto> getPetsPage(@RequestParam("page") int pageNumber) throws ResponseStatusException
    {
        return this.petsService.getPetsPage(pageNumber);
    }
    
    /**
     * Retrieves the requested page of a User's pet posts. Only a limited view of the data is returned
     * (<code>PetInfoDto</code>).
     *
     * @param principal  Currently authenticated User.
     * @param pageNumber Page number to retrieve.
     * @return The requested <code>Page</code> of the User's pet posts.
     * @throws ResponseStatusException If the requested page does not exist (404 Not Found).
     */
    @GetMapping("my_posts")
    public Page<PetInfoDto> getUserPets(@AuthenticationPrincipal Jwt principal,
                                        @RequestParam("page") int pageNumber) throws ResponseStatusException
    {
        return this.petsService.getUserPets(principal.getSubject(), pageNumber);
    }
    
    /**
     * Retrieves a Pet given its primary numerical key.
     *
     * @param id ID of the Pet to retrieve.
     * @return The requested Pet.
     * @throws ResponseStatusException If the requested Pet does not exist (404 Not Found).
     */
    @GetMapping("{id}")
    public PetDto getPetById(@PathVariable long id) throws ResponseStatusException
    {
        return this.petsService.getPetById(id);
    }
    
    /**
     * Deletes a Pet given its primary numerical key. The Pet must've been posted by the currently authenticated User.
     *
     * @param principal Currently authenticated User.
     * @param id        ID of the Pet to delete.
     * @throws ResponseStatusException If the Pet to delete does not exist (404 Not Found) or it wasn't posted by the
     *                                 currently authenticated User (403 Forbidden).
     */
    @DeleteMapping("{id}")
    public void deletePetById(@AuthenticationPrincipal Jwt principal, @PathVariable long id) throws
            ResponseStatusException
    {
        this.petsService.deletePetById(principal.getSubject(), id);
    }
    
    /**
     * Retrieves the requested page of a User's saved pet posts. Only a limited view of the data is returned
     * (<code>PetInfoDto</code>).
     *
     * @param email      Email of the currently authenticated User.
     * @param pageNumber Page number to retrieve.
     * @return The requested Page of the User's saved pet posts.
     * @throws ResponseStatusException If the requested page does not exist (404 Not Found).
     */
    @GetMapping("saved")
    public Page<PetInfoDto> getSavedPets(@AuthenticationPrincipal String email,
                                         @RequestParam("page") int pageNumber) throws ResponseStatusException
    {
        return this.petsService.getSavedPets(email, pageNumber);
    }
    
    /**
     * Posts a Pet for adoption
     *
     * @param email      Email of the currently authenticated User (the poster).
     * @param postPetDto Information of the Pet being posted.
     */
    @PostMapping
    public void postPet(@AuthenticationPrincipal String email, @Valid @RequestBody PostPetDto postPetDto)
    {
        this.petsService.postPet(email, postPetDto);
    }
    
    /**
     * Edits a Pet given its primary numerical key. The Pet must've been posted by the currently authenticated User.
     *
     * @param email      Email of the currently authenticated User.
     * @param postPetDto New information of the Pet.
     * @param id         ID of the Pet to edit.
     * @throws ResponseStatusException If the Pet does not exist (404 Not Found).
     */
    @PutMapping("{id}")
    public void editPet(@AuthenticationPrincipal String email, @Valid @RequestBody PostPetDto postPetDto,
                        @PathVariable long id) throws ResponseStatusException
    {
        this.petsService.editPet(email, postPetDto, id);
    }
    
    /**
     * Toggles the saved state in a Pet post.
     *
     * @param email Email of the currently authenticated User.
     * @param id    Pet ID.
     * @return The new saved state of the specified Pet.
     */
    @PatchMapping("{id}")
    public boolean togglePetSavedState(@AuthenticationPrincipal String email, @PathVariable long id)
    {
        return this.petsService.togglePetSavedState(email, id);
    }
}
