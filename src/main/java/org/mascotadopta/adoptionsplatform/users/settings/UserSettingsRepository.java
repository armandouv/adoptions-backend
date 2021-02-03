package org.mascotadopta.adoptionsplatform.users.settings;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * UserSettings data retrieval functionality.
 */
@Repository
public interface UserSettingsRepository extends CrudRepository<UserSettings, Long>
{
}
