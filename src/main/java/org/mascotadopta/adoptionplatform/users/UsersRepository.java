package org.mascotadopta.adoptionplatform.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Users' data retrieval functionality.
 */
@Repository
public interface UsersRepository extends CrudRepository<User, Long>
{
    Optional<User> findByEmail(String email);
    
    Optional<User> findByUuid(UUID uuid);
    
    boolean existsByUuid(UUID uuid);
    
    boolean existsByEmail(String email);
}
