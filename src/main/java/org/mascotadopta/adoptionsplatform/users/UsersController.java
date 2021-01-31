package org.mascotadopta.adoptionsplatform.users;

import org.mascotadopta.adoptionsplatform.users.dto.CreateUserDto;
import org.mascotadopta.adoptionsplatform.users.dto.UpdateNameDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
     * Registers a User.
     *
     * @param createUserDto Data of the User to create.
     * @throws ResponseStatusException If the User already exists (400 Bad Request).
     */
    @PostMapping
    public void signUp(@Valid @RequestBody CreateUserDto createUserDto) throws ResponseStatusException
    {
        this.usersService.createUser(createUserDto);
    }
    
    /**
     * Updates the name of a User.
     *
     * @param email         Email of the currently authenticated User.
     * @param updateNameDto The new name.
     */
    @PutMapping
    public void updateName(@AuthenticationPrincipal String email, @Valid @RequestBody UpdateNameDto updateNameDto)
    {
        this.usersService.updateName(email, updateNameDto);
    }
}
