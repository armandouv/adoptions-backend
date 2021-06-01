package org.mascotadopta.adoptionplatform.pets.dto;

import lombok.Value;
import org.mascotadopta.adoptionplatform.pets.Pet;

import java.time.LocalDateTime;

/**
 * Data Transfer Object that contains a limited view of a Pet.
 */
@Value
public class PetInfoDto
{
    long id;
    
    LocalDateTime createdDate;
    
    String type;
    
    boolean isActive;
    
    String name;
    
    int zipCode;
    
    String imageUrl = "";
    
    String imageAlt = "";
    
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
