package org.mascotadopta.adoptionsplatform.users;

import org.mascotadopta.adoptionsplatform.users.dto.CreateUserDto;
import org.mascotadopta.adoptionsplatform.users.dto.UpdateNameDto;
import org.mascotadopta.adoptionsplatform.users.settings.UserSettings;
import org.mascotadopta.adoptionsplatform.users.settings.UserSettingsRepository;
import org.mascotadopta.adoptionsplatform.users.settings.dto.UpdateSettingsDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
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
     * Password encoder.
     */
    private final PasswordEncoder passwordEncoder;
    
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
     * @param passwordEncoder        Password encoder.
     * @param usersRepository        Users repository.
     * @param userSettingsRepository UserSettings repository.
     */
    public UsersService(PasswordEncoder passwordEncoder,
                        UsersRepository usersRepository,
                        UserSettingsRepository userSettingsRepository)
    {
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
        this.userSettingsRepository = userSettingsRepository;
    }
    
    /**
     * Creates a User.
     *
     * @param createUserDto The data of the new User.
     * @throws ResponseStatusException If the User already exists (400 Bad Request).
     */
    public void createUser(CreateUserDto createUserDto) throws ResponseStatusException
    {
        String email = createUserDto.getEmail();
        if (usersRepository.existsByEmail(email)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    
        String hashedPassword = passwordEncoder.encode(createUserDto.getPassword());
        User user = new User(createUserDto.getEmail(), hashedPassword, createUserDto.getFirstName(),
                createUserDto.getLastName());
    
        UserSettings settings = new UserSettings();
        this.userSettingsRepository.save(settings);
        user.setSettings(settings);
    
        usersRepository.save(user);
    }
    
    /**
     * Updates the name of a User.
     *
     * @param email         The email address of the User to update.
     * @param updateNameDto The new name.
     * @throws ResponseStatusException If the User does not exist (404 Not Found).
     */
    public void updateName(String email, UpdateNameDto updateNameDto) throws ResponseStatusException
    {
        Optional<User> userOptional = this.usersRepository.findByEmail(email);
        if (userOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        User user = userOptional.get();
        
        user.setFirstName(updateNameDto.getFirstName());
        user.setLastName(updateNameDto.getLastName());
        this.usersRepository.save(user);
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
    
}
