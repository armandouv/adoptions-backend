package org.mascotadopta.adoptionsplatform.users.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Necessary data to update the name of a User.
 */
@Data
public class UpdateNameDto
{
    @NotBlank
    private String firstName;
    
    @NotBlank
    private String lastName;
}
