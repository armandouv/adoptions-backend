package org.mascotadopta.adoptionsplatform.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Users' data retrieval functionality.
 */
@Repository
public interface UsersRepository extends CrudRepository<User, Long>
{
}
