package org.mascotadopta.adoptionplatform.adoptions.survey;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Responses to the survey required in the adoption application process.
 */
@Data
@NoArgsConstructor
@Entity(name = "QuestionnaireResponses")
@EntityListeners(AuditingEntityListener.class)
public class Survey
{
    /**
     * Primary numerical key.
     */
    @Id
    @GeneratedValue
    private Long id;
    // TODO: Add questions.
}
