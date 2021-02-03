package org.mascotadopta.adoptionsplatform.users.settings.dto;

import javax.validation.constraints.NotNull;

/**
 * Necessary data to update the settings of a User.
 */
public class UpdateSettingsDto
{
    /**
     * Whether the User should receive an email notification when another User applies to their adoption post or not.
     */
    @NotNull
    private final boolean notifyNewApplicants;
    
    /**
     * Whether the User should receive an email notification when their application is accepted or not.
     */
    @NotNull
    private final boolean notifyAcceptedApplications;
    
    /**
     * Whether the User should receive an email notification when their application is rejected or not.
     */
    @NotNull
    private final boolean notifyRejectedApplications;
    
    /**
     * Single constructor.
     *
     * @param notifyNewApplicants        Whether the User should receive an email notification when another User applies
     *                                   to their adoption post or not.
     * @param notifyAcceptedApplications Whether the User should receive an email notification when their application is
     *                                   accepted or not.
     * @param notifyRejectedApplications Whether the User should receive an email notification when their application is
     *                                   rejected or not.
     */
    public UpdateSettingsDto(@NotNull boolean notifyNewApplicants,
                             @NotNull boolean notifyAcceptedApplications,
                             @NotNull boolean notifyRejectedApplications)
    {
        this.notifyNewApplicants = notifyNewApplicants;
        this.notifyAcceptedApplications = notifyAcceptedApplications;
        this.notifyRejectedApplications = notifyRejectedApplications;
    }
    
    /**
     * @return Whether the User should receive an email notification when another User applies to their adoption post or
     * not.
     */
    public boolean isNotifyNewApplicants()
    {
        return notifyNewApplicants;
    }
    
    /**
     * @return Whether the User should receive an email notification when their application is accepted or not.
     */
    public boolean isNotifyAcceptedApplications()
    {
        return notifyAcceptedApplications;
    }
    
    /**
     * @return Whether the User should receive an email notification when their application is rejected or not.
     */
    public boolean isNotifyRejectedApplications()
    {
        return notifyRejectedApplications;
    }
}
