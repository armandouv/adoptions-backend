package org.mascotadopta.adoptionplatform.adoptions.dto;

import lombok.Data;
import org.mascotadopta.adoptionplatform.adoptions.AdoptionApplicationStatus;

import javax.validation.constraints.NotNull;

/**
 * Necessary data to update the status of an AdoptionApplication.
 */
@Data
public class AdoptionApplicationStatusDto
{
    @NotNull
    private AdoptionApplicationStatus status;
}
