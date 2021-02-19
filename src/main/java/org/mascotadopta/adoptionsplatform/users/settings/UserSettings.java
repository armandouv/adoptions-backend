package org.mascotadopta.adoptionsplatform.users.settings;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.mascotadopta.adoptionsplatform.users.settings.dto.UpdateSettingsDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * A User's configuration.
 */
@Data
@NoArgsConstructor
@Entity(name = "UserSettings")
public class UserSettings
{
    /**
     * Primary numerical key.
     */
    @Id
    @GeneratedValue
    @NotNull
    private Long id;
    
    /**
     * Whether the User should receive an email notification when another User applies to their adoption post or not.
     */
    @NotNull
    private boolean notifyNewApplicants = true;
    
    /**
     * Whether the User should receive an email notification when their application is accepted or not.
     */
    @NotNull
    private boolean notifyAcceptedApplications = true;
    
    /**
     * Whether the User should receive an email notification when their application is rejected or not.
     */
    @NotNull
    private boolean notifyRejectedApplications = true;
    
    /**
     * Updates this settings given a DTO.
     *
     * @param updateSettingsDto New settings.
     */
    public void updateFromDto(UpdateSettingsDto updateSettingsDto)
    {
        this.notifyNewApplicants = updateSettingsDto.isNotifyNewApplicants();
        this.notifyAcceptedApplications = updateSettingsDto.isNotifyAcceptedApplications();
        this.notifyRejectedApplications = updateSettingsDto.isNotifyRejectedApplications();
    }
}
