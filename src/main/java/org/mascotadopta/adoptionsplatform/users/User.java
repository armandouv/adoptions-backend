package org.mascotadopta.adoptionsplatform.users;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * A registered user.
 */
@Entity
public class User
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
    
    public String getEmail()
    {
        return email;
    }
    
    public String getPassword()
    {
        return password;
    }
}
