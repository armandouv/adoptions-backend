package org.mascotadopta.adoptionplatform.pets.dto;

import lombok.Value;
import org.mascotadopta.adoptionplatform.pets.Pet;
import org.mascotadopta.adoptionplatform.pets.PetType;

import java.time.LocalDateTime;

/**
 * Data Transfer Object that contains a limited view of a Pet.
 */
@Value
public class PetInfoDto
{
    Long id;
    
    LocalDateTime createdDate;
    
    PetType type;
    
    Boolean active;
    
    String name;
    
    Integer zipCode;
    
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
        this.active = pet.getActive();
        this.name = pet.getName();
        this.zipCode = pet.getZipCode();
    }
}
