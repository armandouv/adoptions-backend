package org.mascotadopta.adoptionsplatform.pets;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * A pet for adoption.
 */
@Entity
public class Pet
{
    /**
     * Primary numerical key.
     */
    @Id
    private long id;
}
