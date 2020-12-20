package org.mascotadopta.adoptionsplatform.adoptions;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Responses to the questionnaire required in the adoption application process.
 */
@Entity
public class QuestionnaireResponses
{
    /**
     * Primary numerical key.
     */
    @Id
    @GeneratedValue()
    private long id;
}
