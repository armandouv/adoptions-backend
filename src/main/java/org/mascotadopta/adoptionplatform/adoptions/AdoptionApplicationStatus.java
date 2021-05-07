package org.mascotadopta.adoptionplatform.adoptions;

/**
 * Represents all possible states of an application.
 */
public enum AdoptionApplicationStatus
{
    /**
     * Initial value. The applicant doesn't have access to the poster's data yet, but hasn't been rejected yet.
     */
    IN_PROGRESS,
    
    /**
     * The poster has accepted the application and let the applicant see their information.
     */
    ACCEPTED,
    
    /**
     * The poster has explicitly let the applicant know that, at the moment, they won't be able to see their contact
     * information. However, this may change in the future.
     */
    REJECTED
}
