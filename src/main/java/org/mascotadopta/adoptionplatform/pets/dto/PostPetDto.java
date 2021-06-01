package org.mascotadopta.adoptionplatform.pets.dto;

import lombok.Data;
import org.mascotadopta.adoptionplatform.pets.PetType;

import javax.validation.constraints.*;

/**
 * Necessary data to post a Pet.
 */
@Data
public class PostPetDto
{
    @NotNull
    private PetType type;
    
    @Size(max = 50)
    @NotBlank
    private String name;
    
    @Size(min = 10, max = 100)
    @NotBlank
    private String description;
    
    @Max(99999)
    @Positive
    @NotNull
    private Integer zipCode;
}
