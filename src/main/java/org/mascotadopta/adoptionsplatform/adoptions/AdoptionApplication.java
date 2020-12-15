package org.mascotadopta.adoptionsplatform.adoptions;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * An adoption request.
 */
@Entity
public class AdoptionApplication
{
    @Id
    private Integer id;
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
}
