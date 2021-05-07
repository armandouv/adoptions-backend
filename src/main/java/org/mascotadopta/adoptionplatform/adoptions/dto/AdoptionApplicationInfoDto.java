package org.mascotadopta.adoptionplatform.adoptions.dto;

import lombok.Value;
import org.mascotadopta.adoptionplatform.adoptions.AdoptionApplication;
import org.mascotadopta.adoptionplatform.adoptions.AdoptionApplicationStatus;
import org.mascotadopta.adoptionplatform.pets.Pet;

import java.time.LocalDateTime;

/**
 * Data Transfer Object that contains a limited view of an AdoptionApplication.
 */
@Value
public class AdoptionApplicationInfoDto
{
    AdoptionApplicationStatus status;
    
    long id;
    
    LocalDateTime createdDate;
    
    Pet pet;
    
    /**
     * Single constructor. Fills the required data given an <code>AdoptionApplication</code>.
     *
     * @param adoptionApplication <code>AdoptionApplication</code> to build this DTO from.
     */
    public AdoptionApplicationInfoDto(AdoptionApplication adoptionApplication)
    {
        this.status = adoptionApplication.getStatus();
        this.id = adoptionApplication.getId();
        this.createdDate = adoptionApplication.getCreatedDate();
        this.pet = adoptionApplication.getPet();
    }
}
