package org.mascotadopta.adoptionsplatform.pets.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PostPetDto
{
    
    /**
     * Animal type of this Pet, e.g. a dog, cat, etc.
     */
    @NotBlank
    private String type;
    
    /**
     * The name of this Pet.
     */
    @NotBlank
    private String name;
    
    /**
     * Relevant information about this Pet.
     */
    @NotBlank
    private String description;
    
    /**
     * The zip code of the place where this Pet is being posted or offered.
     */
    @NotNull
    private int zipCode;
    
    /**
     * @return Animal type of this Pet, e.g. a dog, cat, etc.
     */
    public String getType()
    {
        return type;
    }
    
    /**
     * @return The name of this Pet.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * @return Relevant information about this Pet.
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * @return The zip code of the place where this Pet is being posted or offered.
     */
    public int getZipCode()
    {
        return zipCode;
    }
}
