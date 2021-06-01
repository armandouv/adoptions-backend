package org.mascotadopta.adoptionplatform.adoptions.dto;

import lombok.Value;
import org.mascotadopta.adoptionplatform.adoptions.AdoptionApplication;
import org.mascotadopta.adoptionplatform.adoptions.AdoptionApplicationStatus;
import org.mascotadopta.adoptionplatform.adoptions.QuestionnaireResponses;
import org.mascotadopta.adoptionplatform.pets.Pet;

import java.time.LocalDateTime;

/**
 * Data Transfer Object that contains a detailed view of an AdoptionApplication.
 */
@Value
public class AdoptionApplicationDto
{
    QuestionnaireResponses questionnaireResponses;
    
    AdoptionApplicationStatus status;
    
    Long id;
    
    LocalDateTime createdDate;
    
    Pet pet;
    
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
        this.questionnaireResponses = adoptionApplication.getQuestionnaireResponses();
    }
}
