package org.mascotadopta.adoptionplatform.adoptions;

import org.mascotadopta.adoptionplatform.adoptions.dto.AdoptionApplicationDto;
import org.mascotadopta.adoptionplatform.adoptions.dto.AdoptionApplicationInfoDto;
import org.mascotadopta.adoptionplatform.adoptions.dto.AdoptionApplicationStatusDto;
import org.mascotadopta.adoptionplatform.adoptions.dto.PostAdoptionApplicationDto;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
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
     * @param principal  Currently authenticated User.
     * @param pageNumber Page number to retrieve.
     * @return The requested <code>Page</code> of the User's applications.
     * @throws ResponseStatusException If the requested page does not exist (404 Not Found).
     */
    @GetMapping("my_applications")
    public Page<AdoptionApplicationInfoDto> getUserApplications(@AuthenticationPrincipal Jwt principal,
                                                                @RequestParam("page") int pageNumber) throws
            ResponseStatusException
    {
        return this.adoptionsService.getUserApplications(principal.getSubject(), pageNumber);
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
     * @param oidcUser                   Applicant
     * @param postAdoptionApplicationDto Information needed to apply for the adoption of a Pet.
     */
    @PostMapping
    public void postApplication(@AuthenticationPrincipal OidcUser oidcUser,
                                @Valid @RequestBody PostAdoptionApplicationDto postAdoptionApplicationDto)
    {
        this.adoptionsService.postApplication(oidcUser.getSubject(), postAdoptionApplicationDto);
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
    
    /**
     * Updates the status of the specified application.
     *
     * @param email                        Email of the currently authenticated User, who must be the poster of the Pet
     *                                     for which the application is.
     * @param id                           ID of the application to update.
     * @param adoptionApplicationStatusDto Necessary information to update the application status.
     * @throws ResponseStatusException If the specified application does not exist (404 Not Found) or the currently
     *                                 authenticated User is not the poster of the Pet for which the application is.
     */
    @PatchMapping("{id}")
    public void updateApplicationStatus(@AuthenticationPrincipal String email, @PathVariable long id, @RequestBody
            AdoptionApplicationStatusDto adoptionApplicationStatusDto) throws ResponseStatusException
    {
        this.adoptionsService.updateApplicationStatus(email, id, adoptionApplicationStatusDto.getStatus());
    }
}
