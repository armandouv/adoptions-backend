package org.mascotadopta.adoptionsplatform.auth.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mascotadopta.adoptionsplatform.auth.AuthConstants;
import org.mascotadopta.adoptionsplatform.auth.JwtService;
import org.mascotadopta.adoptionsplatform.auth.dto.UserCredentialsDto;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
            UserCredentialsDto userCredentialsDto = new ObjectMapper()
                    .readValue(request.getInputStream(), UserCredentialsDto.class);
    
            Authentication authentication = new UsernamePasswordAuthenticationToken(userCredentialsDto.getEmail(),
                    userCredentialsDto.getPassword());
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
        response.setHeader(HttpHeaders.AUTHORIZATION,
                AuthConstants.TOKEN_PREFIX + jwtService.generateAccessToken(authResult.getName()));
    
        Cookie refreshTokenCookie = new Cookie(AuthConstants.REFRESH_TOKEN_COOKIE_NAME,
                jwtService.generateRefreshToken(authResult.getName()));
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setMaxAge(
                Math.toIntExact(TimeUnit.MILLISECONDS.toSeconds(AuthConstants.REFRESH_TOKEN_DURATION_MS)));
        refreshTokenCookie.setPath("/auth");
    
        response.setHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString() + AuthConstants.SAME_SITE);
    }
}
