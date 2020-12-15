package org.mascotadopta.adoptionsplatform.adoptions;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Adoptions' data retrieval functionality.
 */
@Repository
public interface AdoptionsRepository extends CrudRepository<AdoptionApplication, Integer>
{
}
