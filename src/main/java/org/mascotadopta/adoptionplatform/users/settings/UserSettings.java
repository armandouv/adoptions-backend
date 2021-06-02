package org.mascotadopta.adoptionplatform.users.settings;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.mascotadopta.adoptionplatform.users.settings.dto.UpdateSettingsDto;

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
    // TODO: Delete NotNull in primary keys.
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
    private Boolean notifyNewApplicants = true;
    
    /**
     * Whether the User should receive an email notification when their application is accepted or not.
     */
    @NotNull
    private Boolean notifyAcceptedApplications = true;
    
    /**
     * Whether the User should receive an email notification when their application is rejected or not.
     */
    @NotNull
    private Boolean notifyRejectedApplications = true;
    
    /**
     * Updates this settings given a DTO.
     *
     * @param updateSettingsDto New settings.
     */
    public void updateFromDto(UpdateSettingsDto updateSettingsDto)
    {
        this.notifyNewApplicants = updateSettingsDto.getNotifyNewApplicants();
        this.notifyAcceptedApplications = updateSettingsDto.getNotifyAcceptedApplications();
        this.notifyRejectedApplications = updateSettingsDto.getNotifyRejectedApplications();
    }
}
