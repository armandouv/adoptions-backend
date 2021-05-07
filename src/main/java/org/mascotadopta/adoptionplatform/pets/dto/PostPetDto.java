package org.mascotadopta.adoptionplatform.pets.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Necessary data to post a Pet.
 */
@Data
public class PostPetDto
{
    @NotBlank
    private String type;
    
    @NotBlank
    private String name;
    
    @NotBlank
    private String description;
    
    @NotNull
    private int zipCode;
}
