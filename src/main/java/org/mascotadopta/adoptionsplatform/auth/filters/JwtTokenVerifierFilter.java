package org.mascotadopta.adoptionsplatform.auth.filters;

import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.jetbrains.annotations.NotNull;
import org.mascotadopta.adoptionsplatform.auth.AuthConstants;
import org.mascotadopta.adoptionsplatform.auth.JwtService;
import org.springframework.http.HttpHeaders;
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
import java.util.ArrayList;

/**
 * Attempts to authenticate a User given an access token.
 */
@Component
public class JwtTokenVerifierFilter extends OncePerRequestFilter
{
    /**
     * JWT utilities.
     */
    private final JwtService jwtService;
    
    /**
     * Single constructor.
     *
     * @param jwtService JWT utilities.
     */
    public JwtTokenVerifierFilter(JwtService jwtService)
    {
        this.jwtService = jwtService;
    }
    
    /**
     * If a valid token is provided in the Authorization header, authenticates a User successfully. Else, performs the
     * remaining filtering.
     *
     * @param request     Incoming request containing a token in the Authorization header.
     * @param response    Outgoing response.
     * @param filterChain Filter chain.
     * @throws IOException      If an I/O error occurs during the processing of the request.
     * @throws ServletException If the filtering fails for any other reason.
     */
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException
    {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(AuthConstants.TOKEN_PREFIX))
        {
            filterChain.doFilter(request, response);
            return;
        }
        
        try
        {
            Claims claims = jwtService.decodeToken(authorizationHeader.substring(7));
            Authentication authentication = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
                    new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        catch (JwtException e)
        {
            throw new IllegalStateException("Invalid token");
        }
        
        filterChain.doFilter(request, response);
    }
}
