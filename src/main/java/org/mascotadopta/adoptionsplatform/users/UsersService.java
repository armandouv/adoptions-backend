package org.mascotadopta.adoptionsplatform.users;

import org.mascotadopta.adoptionsplatform.users.dto.CreateUserDto;
import org.mascotadopta.adoptionsplatform.users.dto.UpdateNameDto;
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
     * Single constructor.
     *
     * @param passwordEncoder Password encoder.
     * @param usersRepository Users repository.
     */
    public UsersService(PasswordEncoder passwordEncoder,
                        UsersRepository usersRepository)
    {
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
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
    
        usersRepository.save(user);
    }
    
    /**
     * Updates the name of a User.
     *
     * @param email         The email of the User to update.
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
}
