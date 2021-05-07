package org.mascotadopta.adoptionsplatform.users;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.mascotadopta.adoptionsplatform.pets.Pet;
import org.mascotadopta.adoptionsplatform.users.settings.UserSettings;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
    @Size(min = 1)
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
    @NotNull
    private UUID uuid;
    
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
     * @param uuid User ID generated in identity provider.
     */
    public User(UUID uuid)
    {
        this.uuid = uuid;
    }
}
