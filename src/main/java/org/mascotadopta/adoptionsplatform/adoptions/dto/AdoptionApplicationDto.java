package org.mascotadopta.adoptionsplatform.adoptions.dto;

import org.mascotadopta.adoptionsplatform.adoptions.AdoptionApplication;
import org.mascotadopta.adoptionsplatform.adoptions.AdoptionApplicationStatus;
import org.mascotadopta.adoptionsplatform.pets.Pet;

import java.time.LocalDateTime;

public class AdoptionApplicationDto
{
    /**
     * The status of this application.
     */
    private final AdoptionApplicationStatus status;
    
    /**
     * Primary numerical key.
     */
    private final long id;
    
    /**
     * Date of application creation.
     */
    private final LocalDateTime createdDate;
    
    /**
     * Pet for which this application is.
     */
    private final Pet pet;
    
    /**
     * Single constructor. Fills the required data given an <code>AdoptionApplication</code>.
     *
     * @param adoptionApplication <code>AdoptionApplication</code> to build this DTO from.
     */
    public AdoptionApplicationDto(AdoptionApplication adoptionApplication)
    {
        this.status = adoptionApplication.getStatus();
        this.id = adoptionApplication.getId();
        this.createdDate = adoptionApplication.getCreatedDate();
        this.pet = adoptionApplication.getPet();
    }
}
