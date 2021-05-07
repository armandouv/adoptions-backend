package org.mascotadopta.adoptionplatform.users.settings.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Necessary data to update the settings of a User.
 */
@Data
public class UpdateSettingsDto
{
    @NotNull
    private boolean notifyNewApplicants;
    
    @NotNull
    private boolean notifyAcceptedApplications;
    
    @NotNull
    private boolean notifyRejectedApplications;
}
