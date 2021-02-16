package org.mascotadopta.adoptionsplatform.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Credentials needed to authenticate a User.
 */
@Data
public class UserCredentialsDto
{
    @NotBlank
    private String email;
    
    @NotBlank
    private String password;
}
