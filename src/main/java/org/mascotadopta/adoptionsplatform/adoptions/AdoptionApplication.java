package org.mascotadopta.adoptionsplatform.adoptions;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * An adoption request.
 */
@Entity
public class AdoptionApplication
{
    /**
     * Primary numerical key.
     */
    @Id
    private long id;
}
