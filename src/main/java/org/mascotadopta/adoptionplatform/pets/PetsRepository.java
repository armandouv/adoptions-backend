package org.mascotadopta.adoptionplatform.pets;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Pets' data retrieval functionality.
 */
@Repository
public interface PetsRepository extends CrudRepository<Pet, Long>
{
    Page<Pet> findAllByPostedByAuthServerId(String authServerId, Pageable pageable);
    
    Page<Pet> findAllBySavedByAuthServerId(String authServerId, Pageable pageable);
    
    Page<Pet> findAll(Pageable page);
    
    long countApplicationsById(long petId);
}
