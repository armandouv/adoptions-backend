package org.mascotadopta.adoptionsplatform.auth.dto;

import javax.validation.constraints.NotBlank;

/**
 * Credentials needed to authenticate a User.
 */
public class UserCredentialsDto
{
    /**
     * Email to attempt authentication in.
     */
    @NotBlank
    private String email;
    
    /**
     * Password associated with <code>email</code>.
     */
    @NotBlank
    private String password;
    
    /**
     * @return Password associated with this credentials.
     */
    public String getPassword()
    {
        return password;
    }
    
    /**
     * @return Email associated with this credentials.
     */
    public String getEmail()
    {
        return email;
    }
}
