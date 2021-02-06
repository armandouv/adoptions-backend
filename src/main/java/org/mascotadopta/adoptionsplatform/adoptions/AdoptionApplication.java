package org.mascotadopta.adoptionsplatform.adoptions;

import org.mascotadopta.adoptionsplatform.pets.Pet;
import org.mascotadopta.adoptionsplatform.users.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * An adoption request.
 */
@Entity(name = "AdoptionApplication")
@EntityListeners(AuditingEntityListener.class)
public class AdoptionApplication
{
    /**
     * The status of this application.
     */
    private final AdoptionApplicationStatus status = AdoptionApplicationStatus.IN_PROGRESS;
    
    /**
     * Primary numerical key.
     */
    @Id
    @GeneratedValue
    @NotNull
    private long id;
    
    /**
     * Date of application creation.
     */
    @CreatedDate
    @NotNull
    private LocalDateTime createdDate;
    
    public User getUser()
    {
        return user;
    }
    
    /**
     * User who applied for a Pet's adoption.
     */
    @ManyToOne
    @NotNull
    private User user;
    
    /**
     * Pet for which this application is.
     */
    @OneToOne
    @NotNull
    private Pet pet;
    
    /**
     * Responses to the questionnaire required in the application process.
     */
    @OneToOne
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
    
    /**
     * Empty constructor.
     */
    public AdoptionApplication()
    {
    
    }
    
    /**
     * @return Responses to the questionnaire required in the application process.
     */
    public QuestionnaireResponses getQuestionnaireResponses()
    {
        return questionnaireResponses;
    }
    
    /**
     * @return The current status of this application.
     */
    public AdoptionApplicationStatus getStatus()
    {
        return status;
    }
    
    /**
     * @return The id of this application.
     */
    public long getId()
    {
        return id;
    }
    
    /**
     * @return The timestamp when this application was created.
     */
    public LocalDateTime getCreatedDate()
    {
        return createdDate;
    }
    
    /**
     * @return The pet for which this application is.
     */
    public Pet getPet()
    {
        return pet;
    }
}
