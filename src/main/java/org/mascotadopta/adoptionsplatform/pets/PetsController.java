package org.mascotadopta.adoptionsplatform.pets;

import org.mascotadopta.adoptionsplatform.pets.dto.PetInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
}
