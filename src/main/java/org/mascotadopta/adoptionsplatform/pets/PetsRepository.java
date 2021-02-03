package org.mascotadopta.adoptionsplatform.pets;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;

/**
 * Pets' data retrieval functionality.
 */
@Repository
public interface PetsRepository extends CrudRepository<Pet, Long>
{
    Page<Pet> findAllByPostedByEmail(@NotNull String userEmail, Pageable pageable);
    
    Page<Pet> findAll(Pageable page);
}
