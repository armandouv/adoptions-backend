package org.mascotadopta.adoptionsplatform.adoptions.dto;

import org.mascotadopta.adoptionsplatform.adoptions.QuestionnaireResponses;

import javax.validation.constraints.NotNull;

/**
 * Necessary data to post an AdoptionApplication.
 */
public class PostAdoptionApplicationDto
{
    /**
     * ID of the Pet for which this application is.
     */
    @NotNull
    private Long petId;
    
    /**
     * Responses to the questionnaire required in the application process.
     */
    @NotNull
    private QuestionnaireResponses questionnaireResponses;
    
    public Long getPetId()
    {
        return petId;
    }
    
    public QuestionnaireResponses getQuestionnaireResponses()
    {
        return questionnaireResponses;
    }
}
