package org.mascotadopta.adoptionplatform.users;

import org.mascotadopta.adoptionplatform.users.settings.UserSettings;
import org.mascotadopta.adoptionplatform.users.settings.UserSettingsRepository;
import org.mascotadopta.adoptionplatform.users.settings.dto.UpdateSettingsDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * Users-related business logic.
 */
@Service
public class UsersService
{
    /**
     * Users repository.
     */
    private final UsersRepository usersRepository;
    
    /**
     * UserSettings repository.
     */
    private final UserSettingsRepository userSettingsRepository;
    
    /**
     * Single constructor.
     *
     * @param usersRepository        Users repository.
     * @param userSettingsRepository UserSettings repository.
     */
    public UsersService(UsersRepository usersRepository,
                        UserSettingsRepository userSettingsRepository)
    {
        this.usersRepository = usersRepository;
        this.userSettingsRepository = userSettingsRepository;
    }
    
    
    /**
     * Updates the settings of a User.
     *
     * @param email             The email address of the User whose settings will be updated.
     * @param updateSettingsDto New settings.
     * @throws ResponseStatusException If the User does not exist (404 Not Found).
     */
    public void updateSettings(String email, UpdateSettingsDto updateSettingsDto) throws ResponseStatusException
    {
        Optional<User> userOptional = this.usersRepository.findByEmail(email);
        if (userOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        User user = userOptional.get();
    
        UserSettings settings = user.getSettings();
        settings.updateFromDto(updateSettingsDto);
        this.userSettingsRepository.save(settings);
    }
    
    /**
     * Checks if a User is already stored in this server's DB. If it isn't, the corresponding new User would be created
     * and stored.
     *
     * @param oidcUser User to perform the checks in.
     */
    public void processUserRegistration(OidcUser oidcUser)
    {
        String authServerId = oidcUser.getSubject();
        // User already exists in DB.
        if (usersRepository.existsByAuthServerId(authServerId)) return;
        
        UserSettings userSettings = new UserSettings();
        userSettings = userSettingsRepository.save(userSettings);
        
        User newUser = new User(authServerId, oidcUser.getEmail());
        newUser.setSettings(userSettings);
        usersRepository.save(newUser);
    }
}
