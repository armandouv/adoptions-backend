package org.mascotadopta.adoptionplatform.auth;

import org.mascotadopta.adoptionplatform.users.User;
import org.mascotadopta.adoptionplatform.users.UsersRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
public class UserRegistrationFilter extends GenericFilterBean
{
    private final UsersRepository usersRepository;
    
    public UserRegistrationFilter(UsersRepository usersRepository)
    {
        this.usersRepository = usersRepository;
    }
    
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws
            IOException, ServletException
    {
        OidcUser user = (OidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if (user != null)
        {
            UUID userUuid = UUID.fromString(user.getSubject());
            if (usersRepository.existsByUuid(userUuid)) return;
            User newUser = new User(userUuid);
            usersRepository.save(newUser);
        }
    }
}
