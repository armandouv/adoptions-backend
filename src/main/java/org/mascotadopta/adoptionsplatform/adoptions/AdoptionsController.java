package org.mascotadopta.adoptionsplatform.adoptions;

import org.mascotadopta.adoptionsplatform.adoptions.dto.AdoptionApplicationDto;
import org.mascotadopta.adoptionsplatform.adoptions.dto.AdoptionApplicationInfoDto;
import org.mascotadopta.adoptionsplatform.adoptions.dto.PostAdoptionApplicationDto;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

/**
 * Adoptions-related routes.
 */
@RequestMapping("adoptions")
@RestController
public class AdoptionsController
{
    /**
     * Adoptions service.
     */
    private final AdoptionsService adoptionsService;
    
    /**
     * Single constructor.
     *
     * @param adoptionsService Adoptions service.
     */
    public AdoptionsController(AdoptionsService adoptionsService)
    {
        this.adoptionsService = adoptionsService;
    }
    
    /**
     * Retrieves the requested page of a User's adoption applications. Only a limited view of the data is returned
     * (<code>AdoptionApplicationInfoDto</code>).
     *
     * @param email      Email of the currently authenticated User.
     * @param pageNumber Page number to retrieve.
     * @return The requested <code>Page</code> of the User's applications.
     * @throws ResponseStatusException If the requested page does not exist (404 Not Found).
     */
    @GetMapping("my_applications")
    public Page<AdoptionApplicationInfoDto> getUserApplications(@AuthenticationPrincipal String email,
                                                                @RequestParam("page") int pageNumber) throws
            ResponseStatusException
    {
        return this.adoptionsService.getUserApplications(email, pageNumber);
    }
    
    /**
     * Retrieves the requested page of AdoptionApplications information.
     *
     * @param pageNumber Page number to retrieve.
     * @return The requested AdoptionApplication information Page.
     * @throws ResponseStatusException If the requested page does not exist (404 Not Found).
     */
    @GetMapping
    public Page<AdoptionApplicationInfoDto> getApplicationsPage(@RequestParam("page") int pageNumber) throws
            ResponseStatusException
    {
        return this.adoptionsService.getApplicationsPage(pageNumber);
    }
    
    /**
     * Posts an adoption application for a Pet.
     *
     * @param email                      Email of the applicant.
     * @param postAdoptionApplicationDto Information needed to apply for the adoption of a Pet.
     */
    @PostMapping
    public void postApplication(@AuthenticationPrincipal String email,
                                @Valid @RequestBody PostAdoptionApplicationDto postAdoptionApplicationDto)
    {
        this.adoptionsService.postApplication(email, postAdoptionApplicationDto);
    }
    
    /**
     * Retrieves a User's AdoptionApplication, given its primary numerical key.
     *
     * @param email Email of the currently authenticated User.
     * @param id    ID of the application to retrieve.
     * @return The requested application.
     * @throws ResponseStatusException If the requested AdoptionApplication does not exist (404 Not Found) or it wasn't
     *                                 posted by the authenticated User (403 Forbidden).
     */
    @GetMapping("{id}")
    public AdoptionApplicationDto getApplicationById(@AuthenticationPrincipal String email,
                                                     @PathVariable long id) throws ResponseStatusException
    {
        return this.adoptionsService.getApplicationById(email, id);
    }
    
    /**
     * Withdraws the specified application for a Pet.
     *
     * @param email Email of the currently authenticated User.
     * @param id    ID of the application to withdraw.
     * @throws ResponseStatusException If the specified application does not exist (404 Not Found) or the applicant is
     *                                 not the authenticated User (403 Forbidden).
     */
    @DeleteMapping("{id}")
    public void withdrawApplicationById(@AuthenticationPrincipal String email, @PathVariable long id) throws
            ResponseStatusException
    {
        this.adoptionsService.deleteApplicationById(email, id);
    }
}
