package org.mascotadopta.adoptionsplatform.users.dto;

import javax.validation.constraints.NotBlank;

/**
 * Necessary data to update the name of a User.
 */
public class UpdateNameDto
{
    /**
     * The new first name of the <code>User</code>.
     */
    @NotBlank
    private final String firstName;
    
    /**
     * The new last name of the <code>User</code>.
     */
    @NotBlank
    private final String lastName;
    
    /**
     * Single constructor.
     *
     * @param firstName The new first name of the <code>User</code>.
     * @param lastName  The new last name of the <code>User</code>.
     */
    public UpdateNameDto(@NotBlank String firstName, @NotBlank String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    /**
     * @return The new first name of the <code>User</code>.
     */
    public String getFirstName()
    {
        return firstName;
    }
    
    /**
     * @return The new last name of the <code>User</code>.
     */
    public String getLastName()
    {
        return lastName;
    }
}
