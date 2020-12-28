package org.mascotadopta.adoptionsplatform.auth.dto;

public class UserCredentialsDto
{
    private String email;
    
    private String password;
    
    public String getPassword()
    {
        return password;
    }
    
    public String getEmail()
    {
        return email;
    }
}
