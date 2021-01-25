package org.mascotadopta.adoptionsplatform.adoptions;

import org.mascotadopta.adoptionsplatform.adoptions.dto.AdoptionApplicationDto;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
     * Retrieves the requested page of a User's adoption applications.
     *
     * @param email Email of the currently authenticated User.
     * @param page  Page number to retrieve.
     * @return The requested <code>Page</code> of the User's applications.
     * @throws ResponseStatusException If the requested page does not exist (404 Not Found).
     */
    @RequestMapping("my_applications")
    @GetMapping
    public Page<AdoptionApplicationDto> getApplications(@AuthenticationPrincipal String email,
                                                        @RequestParam("page") int page) throws ResponseStatusException
    {
        return this.adoptionsService.getApplications(email, page);
    }
}
