package org.mascotadopta.adoptionsplatform.auth.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mascotadopta.adoptionsplatform.auth.JwtService;
import org.mascotadopta.adoptionsplatform.auth.dto.EmailAndPasswordDto;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
    private final JwtService jwtService;
    
    public JwtUsernamePasswordAuthenticationFilter(@Lazy AuthenticationManager authenticationManager,
                                                   JwtService jwtService)
    {
        super(authenticationManager);
        this.jwtService = jwtService;
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
            AuthenticationException
    {
        try
        {
            EmailAndPasswordDto emailAndPasswordDto = new ObjectMapper()
                    .readValue(request.getInputStream(), EmailAndPasswordDto.class);
            
            Authentication authentication = new UsernamePasswordAuthenticationToken(emailAndPasswordDto.getEmail(),
                    emailAndPasswordDto.getPassword());
            return getAuthenticationManager().authenticate(authentication);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Invalid authentication request");
        }
    }
    
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException
    {
        response.setHeader("Authorization", "Bearer " + jwtService.generateAccessToken(authResult.getName()));
        response.addCookie(new Cookie("refresh_token", jwtService.generateRefreshToken(authResult.getName())));
    }
}
