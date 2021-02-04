package org.mascotadopta.adoptionsplatform.pets;

import org.mascotadopta.adoptionsplatform.adoptions.AdoptionApplication;
import org.mascotadopta.adoptionsplatform.users.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * A pet for adoption.
 */
@Entity(name = "Pet")
@EntityListeners(AuditingEntityListener.class)
public class Pet
{
    /**
     * Primary numerical key.
     */
    @Id
    @GeneratedValue
    @NotNull
    private long id;
    
    /**
     * Date of Pet creation.
     */
    @CreatedDate
    @NotNull
    private LocalDateTime createdDate;
    
    /**
     * @return User who posted this Pet for adoption.
     */
    public User getPostedBy()
    {
        return postedBy;
    }
    
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
    @NotNull
    private String type;
    
    /**
     * Whether this Pet posting is still available for adoption or not.
     */
    @NotNull
    private boolean isActive;
    
    /**
     * The name of this Pet.
     */
    @NotNull
    private String name;
    
    /**
     * Relevant information about this Pet.
     */
    @NotNull
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
    private int zipCode;
    
    /**
     * @return The id of this Pet.
     */
    public long getId()
    {
        return id;
    }
    
    /**
     * @return The timestamp when this Pet was posted for adoption.
     */
    public LocalDateTime getCreatedDate()
    {
        return createdDate;
    }
    
    /**
     * @return The type of this Pet, e.g. a dog, cat, etc.
     */
    public String getType()
    {
        return type;
    }
    
    /**
     * @return Whether this Pet posting is still available for adoption or not.
     */
    public boolean isActive()
    {
        return isActive;
    }
    
    /**
     * @return The name of this Pet
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * @return Relevant information about this Pet.
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * @return The zip code of the place where this Pet is being posted or offered.
     */
    public int getZipCode()
    {
        return zipCode;
    }
}
