package org.mascotadopta.adoptionsplatform.adoptions;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Adoptions-related routes.
 */
@RestController(value = "adoptions")
public class AdoptionsController
{
    @GetMapping()
    String get()
    {
        return "hola";
    }
}
