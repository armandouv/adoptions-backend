package org.mascotadopta.adoptionplatform.users;

import org.mascotadopta.adoptionplatform.users.settings.dto.UpdateSettingsDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Users-related routes.
 */
@RequestMapping("users")
@RestController
public class UsersController
{
    /**
     * Users service.
     */
    private final UsersService usersService;
    
    /**
     * Single constructor.
     *
     * @param usersService Users service.
     */
    public UsersController(UsersService usersService)
    {
        this.usersService = usersService;
    }
    
    /**
     * Updates the settings of a User.
     *
     * @param principal         Currently authenticated User.
     * @param updateSettingsDto New settings.
     */
    @PutMapping("settings")
    public void updateSettings(@AuthenticationPrincipal Jwt principal,
                               @Valid @RequestBody UpdateSettingsDto updateSettingsDto)
    {
        this.usersService.updateSettings(principal.getSubject(), updateSettingsDto);
    }
}
