package org.mascotadopta.adoptionsplatform.pets;

import org.mascotadopta.adoptionsplatform.pets.dto.PetDto;
import org.mascotadopta.adoptionsplatform.pets.dto.PetInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
     * @param email      Email of the currently authenticated User.
     * @param pageNumber Page number to retrieve.
     * @return The requested <code>Page</code> of the User's pet posts.
     * @throws ResponseStatusException If the requested page does not exist (404 Not Found).
     */
    @RequestMapping("my_posts")
    @GetMapping
    public Page<PetInfoDto> getUserPosts(@AuthenticationPrincipal String email,
                                         @RequestParam("page") int pageNumber) throws ResponseStatusException
    {
        return this.petsService.getUserPosts(email, pageNumber);
    }
    
    /**
     * Retrieves a Pet given its primary numerical key.
     *
     * @param id ID of the Pet to retrieve.
     * @return The requested Pet.
     */
    @RequestMapping("{id}")
    @GetMapping
    public PetDto getPetById(@PathVariable long id)
    {
        return this.petsService.getPetById(id);
    }
}
