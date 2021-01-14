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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Replaces the standard <code>UsernamePasswordAuthenticationFilter</code>. Generates access and refresh tokens if the
 * <code>User</code> successfully authenticates with its username and password, and adds them to their respective
 * places in the request.
 */
public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
    /**
     * JWT utilities.
     */
    private final JwtService jwtService;
    
    /**
     * Constructs this filter with the specified dependencies.
     *
     * @param authenticationManager Authentication manager. The <code>@Lazy annotation</code> is used to avoid a
     *                              circular dependency.
     * @param jwtService            JWT utilities.
     */
    public JwtUsernamePasswordAuthenticationFilter(@Lazy AuthenticationManager authenticationManager,
                                                   JwtService jwtService)
    {
        super(authenticationManager);
        this.jwtService = jwtService;
    }
    
    /**
     * Attempts to authenticate a <code>User</code> from the credentials given in <code>request</code>.
     *
     * @param request  Incoming request containing a <code>User</code>'s credentials. (<code>UserCredentialsDto</code>)
     * @param response Outgoing response.
     * @return The <code>Authentication</code> of this User, if successful.
     * @throws AuthenticationException If credentials are invalid.
     */
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
    
    /**
     * Given that a User's credentials were valid, generates access and refresh tokens and adds it to its corresponding
     * places in <code>response</code>.
     *
     * @param request    Incoming request.
     * @param response   Outgoing response. This is were the tokens will get added.
     * @param chain      Filter chain.
     * @param authResult Authentication of this User.
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult)
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
