package org.mascotadopta.adoptionsplatform.users;

import org.mascotadopta.adoptionsplatform.users.dto.CreateUserDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
}
