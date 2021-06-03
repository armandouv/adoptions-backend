package org.mascotadopta.adoptionplatform.adoptions.dto;

import lombok.Data;
import org.mascotadopta.adoptionplatform.adoptions.questionnaire.QuestionnaireResponses;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * Necessary data to post an AdoptionApplication.
 */
@Data
public class PostAdoptionApplicationDto
{
    @Positive
    @NotNull
    private Long petId;
    
    @NotNull
    private QuestionnaireResponses questionnaireResponses;
}
