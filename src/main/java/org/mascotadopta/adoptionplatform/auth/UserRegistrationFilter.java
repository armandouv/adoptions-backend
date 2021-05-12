package org.mascotadopta.adoptionplatform.auth;

import org.jetbrains.annotations.NotNull;
import org.mascotadopta.adoptionplatform.users.UsersService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class UserRegistrationFilter extends OncePerRequestFilter
{
    private final UsersService usersService;
    
    public UserRegistrationFilter(UsersService usersService)
    {
        this.usersService = usersService;
    }
    
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Jwt) usersService.processUserRegistration((Jwt) principal);
        filterChain.doFilter(request, response);
    }
}
