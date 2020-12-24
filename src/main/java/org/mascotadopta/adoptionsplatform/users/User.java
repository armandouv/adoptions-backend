package org.mascotadopta.adoptionsplatform.users;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * A registered user.
 */
@Entity
public class User implements UserDetails
{
    /**
     * Represents whether a User has already confirmed their email or not.
     */
    private boolean isEmailConfirmed = false;
    
    /**
     * Primary numerical key.
     */
    @Id
    @GeneratedValue()
    private long id;
    
    /**
     * Date of User creation.
     */
    @CreatedDate
    private LocalDateTime createdDate;
    
    /**
     * Registered user email.
     */
    @Column(unique = true)
    private String email;
    
    /**
     * Password hash.
     */
    private String password;
    
    /**
     * This User's first name.
     */
    private String firstName;
    
    /**
     * This User's last name.
     */
    private String lastName;
    
    public User(String email, String password, String firstName, String lastName)
    {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    protected User()
    {
    
    }
    
    public boolean isEmailConfirmed()
    {
        return isEmailConfirmed;
    }
    
    public void setEmailConfirmed(boolean emailConfirmed)
    {
        isEmailConfirmed = emailConfirmed;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return null;
    }
    
    @Override
    public String getPassword()
    {
        return this.password;
    }
    
    @Override
    public String getUsername()
    {
        return this.email;
    }
    
    @Override
    public boolean isAccountNonExpired()
    {
        return false;
    }
    
    @Override
    public boolean isAccountNonLocked()
    {
        return false;
    }
    
    @Override
    public boolean isCredentialsNonExpired()
    {
        return false;
    }
    
    @Override
    public boolean isEnabled()
    {
        return this.isEmailConfirmed;
    }
}
