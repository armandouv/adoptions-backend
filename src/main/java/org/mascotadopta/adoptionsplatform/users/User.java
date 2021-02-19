package org.mascotadopta.adoptionsplatform.users;

import lombok.Data;
import lombok.NoArgsConstructor;
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
@Data
@NoArgsConstructor
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
    private Set<Pet> savedPets = new HashSet<>();
    
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
}
