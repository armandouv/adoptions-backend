package org.mascotadopta.adoptionsplatform.adoptions;

import org.mascotadopta.adoptionsplatform.pets.Pet;
import org.mascotadopta.adoptionsplatform.users.User;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * An adoption request.
 */
@Entity(name = "AdoptionApplication")
@EntityListeners(AuditingEntityListener.class)
public class AdoptionApplication
{
    /**
     * Whether the applicant has permission to see the poster's data.
     * <p>
     * Initially, the applicant doesn't have access to the data, however, if the application is accepted by the poster,
     * the value of this property will change to true. On the other hand, if the application is rejected, this property
     * will always remain with its initial value (false). In other words, once the value of hasBeenRejected changes to
     * true, mutating isDataVisible (to true) becomes an invalid operation.
     */
    private final boolean isDataVisible = false;
    
    /**
     * Primary numerical key.
     */
    @Id
    @GeneratedValue()
    private long id;
    
    /**
     * Date of User creation.
     */
    @CreatedDate
    private LocalDateTime createdDate;
    
    /**
     * User who applied for a Pet's adoption.
     */
    @OneToOne
    private User by;
    
    /**
     * Pet for which this application is.
     */
    @OneToOne
    private Pet pet;
    
    /**
     * Whether this application has already been rejected.
     */
    private boolean hasBeenRejected;
    
    /**
     * Responses to the questionnaire required in the application process.
     */
    @OneToOne
    private QuestionnaireResponses questionnaireResponses;
}
