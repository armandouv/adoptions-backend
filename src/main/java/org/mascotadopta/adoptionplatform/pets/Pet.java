package org.mascotadopta.adoptionplatform.pets;

import com.google.common.base.MoreObjects;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mascotadopta.adoptionplatform.pets.dto.PostPetDto;
import org.mascotadopta.adoptionplatform.users.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A pet for adoption.
 */
@Data
@NoArgsConstructor
@Entity(name = "Pet")
@EntityListeners(AuditingEntityListener.class)
public class Pet
{
    /**
     * Primary numerical key.
     */
    @Id
    @GeneratedValue
    private Long id;
    
    /**
     * Date of Pet creation.
     */
    @CreatedDate
    @NotNull
    private LocalDateTime createdDate;
    
    /**
     * User who posted this Pet for adoption.
     */
    @ManyToOne
    @NotNull
    private User postedBy;
    
    /**
     * Users who saved this Pet posting.
     */
    @ManyToMany
    private Set<User> savedBy = new HashSet<>();
    
    /**
     * Animal type of this Pet, e.g. a dog, cat, etc.
     */
    @NotNull
    private PetType type;
    
    /**
     * Whether this Pet posting is still available for adoption or not.
     */
    @NotNull
    private Boolean active = true;
    
    /**
     * The name of this Pet.
     */
    @NotBlank
    @Size(max = 50)
    private String name;
    
    /**
     * Relevant information about this Pet.
     */
    @NotBlank
    @Size(min = 10, max = 100)
    private String description;
    
    /**
     * The zip code of the place where this Pet is being posted or offered.
     */
    @Max(99999)
    @Positive
    @NotNull
    private Integer zipCode;
    
    public void deleteSavedByUser(User user)
    {
        this.savedBy.remove(user);
    }
    
    public void addSavedByUser(User user)
    {
        this.savedBy.add(user);
    }
    
    public boolean didUserSave(User user)
    {
        return this.savedBy.contains(user);
    }
    
    /**
     * Constructs a Pet given a DTO and the User who posted it.
     *
     * @param postPetDto Pet DTO.
     * @param postedBy   User who posted the Pet.
     */
    public Pet(PostPetDto postPetDto, User postedBy)
    {
        this.type = postPetDto.getType();
        this.name = postPetDto.getName();
        this.description = postPetDto.getDescription();
        this.zipCode = postPetDto.getZipCode();
        this.postedBy = postedBy;
    }
    
    /**
     * Updates this Pet given the new information in PostPetDto. If there are any null fields in the DTO, the original
     * value doesn't change.
     *
     * @param postPetDto New Pet information.
     */
    public void updateFromDto(PostPetDto postPetDto)
    {
        this.type = MoreObjects.firstNonNull(postPetDto.getType(), this.type);
        this.name = MoreObjects.firstNonNull(postPetDto.getName(), this.name);
        this.description = MoreObjects.firstNonNull(postPetDto.getDescription(), this.description);
        this.zipCode = MoreObjects.firstNonNull(postPetDto.getZipCode(), this.zipCode);
    }
}
