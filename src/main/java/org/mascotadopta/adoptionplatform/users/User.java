package org.mascotadopta.adoptionplatform.users;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.mascotadopta.adoptionplatform.pets.Pet;
import org.mascotadopta.adoptionplatform.users.settings.UserSettings;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
     * Primary numerical key.
     */
    @Id
    @GeneratedValue
    @NotNull
    private Long id;
    
    /**
     * User ID created in identity provider.
     */
    @Column(unique = true)
    @NotBlank
    private String authServerId;
    
    @Column(unique = true)
    @NotNull
    private String email;
    
    /**
     * Date of User creation.
     */
    @CreatedDate
    @NotNull
    private LocalDateTime createdDate;
    
    /**
     * Saved Pet posts.
     */
    @ManyToMany
    private Set<Pet> savedPets = new HashSet<>();
    
    /**
     * Constructs a <code>User</code> with the information provided.
     *
     * @param authServerId User ID generated in identity provider.
     * @param email        User's email
     */
    public User(String authServerId, String email)
    {
        this.authServerId = authServerId;
        this.email = email;
    }
}
