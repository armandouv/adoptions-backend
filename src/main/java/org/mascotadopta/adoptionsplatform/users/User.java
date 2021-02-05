package org.mascotadopta.adoptionsplatform.users;

import org.mascotadopta.adoptionsplatform.pets.Pet;
import org.mascotadopta.adoptionsplatform.users.settings.UserSettings;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A registered user.
 */
@Entity(name = "UserEntity")
@EntityListeners(AuditingEntityListener.class)
public class User
{
    /**
     * Settings associated with this User.
     */
    @OneToOne
    private UserSettings settings;
    
    /**
     * Represents whether a User has already confirmed their email or not.
     */
    private boolean isEmailConfirmed = false;
    
    /**
     * Primary numerical key.
     */
    @Id
    @GeneratedValue
    @NotNull
    private Long id;
    
    /**
     * Date of User creation.
     */
    @CreatedDate
    @NotNull
    private LocalDateTime createdDate;
    
    /**
     * Registered user email.
     */
    @Column(unique = true)
    @NotNull
    private String email;
    
    /**
     * Password hash.
     */
    @NotNull
    private String password;
    
    /**
     * This User's first name.
     */
    @NotNull
    private String firstName;
    
    /**
     * This User's last name.
     */
    @NotNull
    private String lastName;
    
    /**
     * Saved Pet posts.
     */
    @ManyToMany
    private final Set<Pet> savedPets = new HashSet<>();
    
    /**
     * @return Saved Pet posts.
     */
    public Set<Pet> getSavedPets()
    {
        return savedPets;
    }
    
    /**
     * Constructs a <code>User</code> with the information provided.
     *
     * @param email     The email that this <code>User</code> will get assigned to.
     * @param password  The hashed password of this <code>User</code>.
     * @param firstName The name of this <code>User</code>.
     * @param lastName  The last name of this <code>User</code>.
     */
    public User(String email, String password, String firstName, String lastName)
    {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    /**
     * Constructs a <code>User</code> given its email.
     *
     * @param email The email that this <code>User</code> will get assigned to.
     */
    protected User(String email)
    {
        this.email = email;
    }
    
    /**
     * Empty constructor.
     */
    public User()
    {
    
    }
    
    /**
     * @param firstName The new firstName of this User.
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    
    /**
     * @return Settings associated with this User.
     */
    public UserSettings getSettings()
    {
        return settings;
    }
    
    /**
     * @param lastName The new lastName of this User.
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    
    /**
     * Sets this User's settings.
     *
     * @param settings Settings of this User.
     */
    public void setSettings(UserSettings settings)
    {
        this.settings = settings;
    }
    
    /**
     * Checks if this User's email is already confirmed. If it isn't, this account is inactive and should not be allowed
     * to sign in.
     *
     * @return Whether this <code>User</code> has confirmed their email yet or not.
     */
    public boolean isEmailConfirmed()
    {
        return isEmailConfirmed;
    }
    
    /**
     * Update this <code>User</code>'s email confirmation status to validity.
     */
    public void setEmailConfirmed()
    {
        isEmailConfirmed = true;
    }
    
    /**
     * Gets the email assigned to this <code>User</code>.
     *
     * @return The email associated with this <code>User</code>.
     */
    public String getEmail()
    {
        return email;
    }
    
    /**
     * Gets the hashed password of this <code>User</code>.
     *
     * @return The hashed password of this User.
     */
    public String getPassword()
    {
        return password;
    }
}
