package org.mascotadopta.adoptionsplatform.adoptions.dto;

import lombok.Data;
import org.mascotadopta.adoptionsplatform.adoptions.QuestionnaireResponses;

import javax.validation.constraints.NotNull;

/**
 * Necessary data to post an AdoptionApplication.
 */
@Data
public class PostAdoptionApplicationDto
{
    @NotNull
    private Long petId;
    
    @NotNull
    private QuestionnaireResponses questionnaireResponses;
}
