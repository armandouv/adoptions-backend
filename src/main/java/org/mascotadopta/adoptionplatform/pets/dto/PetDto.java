package org.mascotadopta.adoptionplatform.pets.dto;

import lombok.Value;
import org.mascotadopta.adoptionplatform.pets.Pet;
import org.mascotadopta.adoptionplatform.pets.PetType;

import java.time.LocalDateTime;

@Value
public class PetDto
{
    Long id;
    
    LocalDateTime createdDate;
    
    PetType type;
    
    Boolean active;
    
    String name;
    
    Integer zipCode;
    
    String description;
    
    /**
     * Number of application processes for this Pet, regardless of status.
     */
    long numberOfApplications;
    
    /**
     * Single constructor. Fills the required data given a <code>Pet</code>.
     *
     * @param pet                  <code>Pet</code> to build this DTO from.
     * @param numberOfApplications Number of application processes for this Pet.
     */
    public PetDto(Pet pet, long numberOfApplications)
    {
        this.id = pet.getId();
        this.createdDate = pet.getCreatedDate();
        this.type = pet.getType();
        this.active = pet.getActive();
        this.name = pet.getName();
        this.zipCode = pet.getZipCode();
        this.description = pet.getDescription();
        this.numberOfApplications = numberOfApplications;
    }
}
