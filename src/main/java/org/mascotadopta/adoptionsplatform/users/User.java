package org.mascotadopta.adoptionsplatform.users;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * A registered user.
 */
@Entity
public class User
{
    /**
     * Primary numerical key.
     */
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
