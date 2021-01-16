package org.mascotadopta.adoptionsplatform.users;

import org.mascotadopta.adoptionsplatform.users.dto.CreateUserDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Users-related routes.
 */
@RequestMapping("/users")
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
     * Registers a User.
     *
     * @param createUserDto Data of the User to create.
     */
    @PostMapping
    public void signUp(@Valid @RequestBody CreateUserDto createUserDto)
    {
        this.usersService.createUser(createUserDto);
    }
}
