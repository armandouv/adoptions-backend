package org.mascotadopta.adoptionsplatform.pets.dto;
import lombok.Getter;
import org.mascotadopta.adoptionsplatform.pets.Pet;

import java.time.LocalDateTime;

/**
 * Data Transfer Object that contains a limited view of a Pet.
 */
@Getter
public class PetInfoDto
{
    /**
     * Primary numerical key.
     */
    private final long id;
    
    /**
     * Date of Pet creation.
     */
    private final LocalDateTime createdDate;
    
    /**
     * Animal type of this Pet, e.g. a dog, cat, etc.
     */
    private final String type;
    
    /**
     * Whether this Pet posting is still available for adoption.
     */
    private final boolean isActive;
    
    /**
     * The name of this Pet.
     */
    private final String name;
    
    /**
     * The zip code of the place where this Pet is being posted or offered.
     */
    private final int zipCode;
    
    /**
     * Single constructor. Fills the required data given a <code>Pet</code>.
     *
     * @param pet <code>Pet</code> to build this DTO from.
     */
    public PetInfoDto(Pet pet)
    {
        this.id = pet.getId();
        this.createdDate = pet.getCreatedDate();
        this.type = pet.getType();
        this.isActive = pet.isActive();
        this.name = pet.getName();
        this.zipCode = pet.getZipCode();
    }
}
