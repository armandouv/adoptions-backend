package org.mascotadopta.adoptionsplatform.pets;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * A pet for adoption.
 */
@Entity
public class Pet
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
