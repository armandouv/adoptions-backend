package org.mascotadopta.adoptionsplatform.pets.dto;

import org.mascotadopta.adoptionsplatform.pets.Pet;

/**
 * Data Transfer Object that contains a detailed view of a Pet.
 */
public class PetDto extends PetInfoDto
{
    /**
     * Relevant information about this Pet.
     */
    private final String description;
    
    /**
     * Number of application processes for this Pet, regardless of status.
     */
    private final long numberOfApplications;
    
    /**
     * Single constructor. Fills the required data given a <code>Pet</code>.
     *
     * @param pet <code>Pet</code> to build this DTO from.
     */
    public PetDto(Pet pet, long numberOfApplications)
    {
        super(pet);
        this.description = pet.getDescription();
        this.numberOfApplications = numberOfApplications;
    }
}
