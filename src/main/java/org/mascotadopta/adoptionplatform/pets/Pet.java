package org.mascotadopta.adoptionplatform.pets;

import com.google.common.base.MoreObjects;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mascotadopta.adoptionplatform.adoptions.AdoptionApplication;
import org.mascotadopta.adoptionplatform.pets.dto.PostPetDto;
import org.mascotadopta.adoptionplatform.users.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

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
    private long id;
    
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
    private List<User> savedBy;
    
    /**
     * Animal type of this Pet, e.g. a dog, cat, etc.
     */
    @NotBlank
    @Size(min = 2, message = "Pet type must be at least two characters long.")
    private String type;
    
    /**
     * Whether this Pet posting is still available for adoption or not.
     */
    @NotNull
    private boolean isActive = true;
    
    /**
     * The name of this Pet.
     */
    @NotBlank
    @Size(min = 2, message = "Pet name must be at least two characters long.")
    private String name;
    
    /**
     * Relevant information about this Pet.
     */
    @NotBlank
    @Size(min = 30, message = "Pet description must be at least 30 characters long.")
    private String description;
    
    /**
     * Application processes for this Pet.
     */
    @OneToMany
    private List<AdoptionApplication> applications;
    
    /**
     * The zip code of the place where this Pet is being posted or offered.
     */
    @NotNull
    @Positive
    private Integer zipCode;
    
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
