package org.mascotadopta.adoptionplatform.pets.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * Necessary data to post a Pet.
 */
@Data
public class PostPetDto
{
    @NotBlank
    @Size(min = 2, message = "Pet type must be at least two characters long.")
    private String type;
    
    @NotBlank
    @Size(min = 2, message = "Pet name must be at least two characters long.")
    private String name;
    
    @NotBlank
    @Size(min = 30, message = "Pet description must be at least 30 characters long.")
    private String description;
    
    @NotNull
    @Positive
    private int zipCode;
}
