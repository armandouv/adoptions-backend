package org.mascotadopta.adoptionsplatform.users.details;

import org.mascotadopta.adoptionsplatform.users.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Custom User Details.
 */
public class ApplicationUserDetails implements UserDetails
{
    /**
     * User's email.
     */
    private final String email;
    
    /**
     * User's password.
     */
    private final String password;
    
    /**
     * Whether the email of this <code>User</code> has already been confirmed or not.
     */
    private final boolean isEmailConfirmed;
    
    /**
     * Single constructor. Initializes relevant information given a User.
     *
     * @param user User to represent.
     */
    public ApplicationUserDetails(User user)
    {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.isEmailConfirmed = user.isEmailConfirmed();
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return null;
    }
    
    @Override
    public String getPassword()
    {
        return password;
    }
    
    @Override
    public String getUsername()
    {
        return email;
    }
    
    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }
    
    @Override
    public boolean isEnabled()
    {
        return true;
    }
}
