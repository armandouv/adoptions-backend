package org.mascotadopta.adoptionsplatform.adoptions;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Responses to the questionnaire required in the adoption application process.
 */
@Entity(name = "QuestionnaireResponses")
@EntityListeners(AuditingEntityListener.class)
public class QuestionnaireResponses
{
    /**
     * Primary numerical key.
     */
    @Id
    @GeneratedValue()
    private long id;
}
