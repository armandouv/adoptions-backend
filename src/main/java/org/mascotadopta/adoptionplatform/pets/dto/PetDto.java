package org.mascotadopta.adoptionplatform.pets.dto;

import lombok.Value;
import org.mascotadopta.adoptionplatform.pets.Pet;

import java.time.LocalDateTime;

@Value
public class PetDto
{
    long id;
    
    LocalDateTime createdDate;
    
    String type;
    
    boolean isActive;
    
    String name;
    
    int zipCode;
    
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
        this.isActive = pet.isActive();
        this.name = pet.getName();
        this.zipCode = pet.getZipCode();
        this.description = pet.getDescription();
        this.numberOfApplications = numberOfApplications;
    }
}
