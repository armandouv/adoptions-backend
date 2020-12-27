package org.mascotadopta.adoptionsplatform.auth.filters;

import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.mascotadopta.adoptionsplatform.auth.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenVerifierFilter extends OncePerRequestFilter
{
    private final JwtService jwtService;
    
    public JwtTokenVerifierFilter(JwtService jwtService)
    {
        this.jwtService = jwtService;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException
    {
        String authorizationHeader = request.getHeader("Authorization");
        if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer "))
        {
            filterChain.doFilter(request, response);
            return;
        }
    
        try
        {
            Claims claims = jwtService.decodeToken(authorizationHeader.substring(7));
            Authentication authentication = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        catch (JwtException e)
        {
            throw new IllegalStateException("Invalid token");
        }
    
        filterChain.doFilter(request, response);
    }
}
