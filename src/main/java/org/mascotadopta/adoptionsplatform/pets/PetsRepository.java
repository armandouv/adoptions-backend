package org.mascotadopta.adoptionsplatform.pets;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Pets' data retrieval functionality.
 */
@Repository
public interface PetsRepository extends CrudRepository<Pet, Long>
{
}
