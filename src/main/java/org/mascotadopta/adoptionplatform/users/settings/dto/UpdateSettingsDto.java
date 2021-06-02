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
    private Boolean notifyNewApplicants;
    
    @NotNull
    private Boolean notifyAcceptedApplications;
    
    @NotNull
    private Boolean notifyRejectedApplications;
}
