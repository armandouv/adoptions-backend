package org.mascotadopta.adoptionsplatform.adoptions.dto;

import org.mascotadopta.adoptionsplatform.adoptions.AdoptionApplicationStatus;

import javax.validation.constraints.NotNull;

/**
 * Necessary data to update the status of an AdoptionApplication.
 */
public class AdoptionApplicationStatusDto
{
    /**
     * The new status of the application.
     */
    @NotNull
    private AdoptionApplicationStatus status;
    
    public AdoptionApplicationStatus getStatus()
    {
        return status;
    }
}
