package org.mascotadopta.adoptionsplatform.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
    private final JwtService jwtService;
    
    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager,
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
        response.setHeader("Authorization", "Bearer " + jwtService.generateToken(authResult.getName(), new Date()));
    }
}
