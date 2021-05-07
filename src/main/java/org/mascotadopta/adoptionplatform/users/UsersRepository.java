package org.mascotadopta.adoptionplatform.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Users' data retrieval functionality.
 */
@Repository
public interface UsersRepository extends CrudRepository<User, Long>
{
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
}
