package org.mascotadopta.adoptionplatform.adoptions;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.mascotadopta.adoptionplatform.adoptions.questionnaire.QuestionnaireResponses;
import org.mascotadopta.adoptionplatform.pets.Pet;
import org.mascotadopta.adoptionplatform.users.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * An adoption request.
 */
@Data
@NoArgsConstructor
@Entity(name = "AdoptionApplication")
@EntityListeners(AuditingEntityListener.class)
public class AdoptionApplication
{
    /**
     * The status of this application.
     */
    @NotNull
    private AdoptionApplicationStatus status = AdoptionApplicationStatus.IN_PROGRESS;
    
    /**
     * Primary numerical key.
     */
    @Id
    @GeneratedValue
    private Long id;
    
    /**
     * Date of application creation.
     */
    @CreatedDate
    @NotNull
    private LocalDateTime createdDate;
    
    /**
     * User who applied for a Pet's adoption.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private User user;
    
    /**
     * Pet for which this application is.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Pet pet;
    
    /**
     * Responses to the questionnaire required in the application process.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    private QuestionnaireResponses questionnaireResponses;
    
    /**
     * Constructs an AdoptionApplication given the related entities.
     *
     * @param user                   User who posted this application.
     * @param pet                    Pet for which this application is.
     * @param questionnaireResponses Responses to the questionnaire required in the application process.
     */
    public AdoptionApplication(User user, Pet pet, QuestionnaireResponses questionnaireResponses)
    {
        this.user = user;
        this.pet = pet;
        this.questionnaireResponses = questionnaireResponses;
    }
}
