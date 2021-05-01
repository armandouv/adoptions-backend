package org.mascotadopta.adoptionsplatform.users.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Necessary data to create a <code>User</code>.
 */
@Data
public class CreateUserDto
{
    @NotBlank
    private String email;
    
    @NotBlank
    //Add validation
    private String password;
    
    @NotBlank
    private String firstName;
    
    @NotBlank
    private String lastName;
}
