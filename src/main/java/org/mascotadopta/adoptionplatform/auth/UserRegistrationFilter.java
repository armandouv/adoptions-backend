package org.mascotadopta.adoptionplatform.auth;

import org.mascotadopta.adoptionplatform.users.UsersService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Component
public class UserRegistrationFilter extends GenericFilterBean
{
    private final UsersService usersService;
    
    public UserRegistrationFilter(UsersService usersService)
    {
        this.usersService = usersService;
    }
    
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws
            IOException, ServletException
    {
        OidcUser user = (OidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user != null) usersService.processUserRegistration(user);
    }
}
