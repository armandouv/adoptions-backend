package org.mascotadopta.adoptionsplatform.adoptions.dto;

import org.mascotadopta.adoptionsplatform.adoptions.AdoptionApplication;
import org.mascotadopta.adoptionsplatform.adoptions.QuestionnaireResponses;

/**
 * Data Transfer Object that contains a detailed view of an AdoptionApplication.
 */
public class AdoptionApplicationDto extends AdoptionApplicationInfoDto
{
    private final QuestionnaireResponses questionnaireResponses;
    
    /**
     * Single constructor. Fills the required data given an <code>AdoptionApplication</code>.
     *
     * @param adoptionApplication <code>AdoptionApplication</code> to build this DTO from.
     */
    public AdoptionApplicationDto(AdoptionApplication adoptionApplication)
    {
        super(adoptionApplication);
        this.questionnaireResponses = adoptionApplication.getQuestionnaireResponses();
    }
}
