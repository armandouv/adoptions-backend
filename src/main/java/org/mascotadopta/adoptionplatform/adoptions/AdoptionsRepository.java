package org.mascotadopta.adoptionplatform.adoptions;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;

/**
 * Adoptions' data retrieval functionality.
 */
@Repository
public interface AdoptionsRepository extends CrudRepository<AdoptionApplication, Long>
{
    Page<AdoptionApplication> findAllByUserEmail(@NotNull String userEmail, Pageable pageable);
    
    Page<AdoptionApplication> findAll(Pageable page);
}
