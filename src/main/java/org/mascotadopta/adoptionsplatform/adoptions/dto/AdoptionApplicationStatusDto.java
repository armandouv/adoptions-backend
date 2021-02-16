package org.mascotadopta.adoptionsplatform.adoptions.dto;

import lombok.Data;
import org.mascotadopta.adoptionsplatform.adoptions.AdoptionApplicationStatus;

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
