package org.mascotadopta.adoptionsplatform.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Supplier;

/**
 * JSON Web Tokens related functionality.
 */
@Service
public class JwtService
{
    private final SecretKey SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    public String generateAccessToken(String email)
    {
        return generateToken(email, this::getAccessTokenExpirationDate);
    }
    
    public String generateRefreshToken(String email)
    {
        return generateToken(email, this::getRefreshTokenExpirationDate);
    }
    
    private String generateToken(String email, Supplier<Date> expirationDateSupplier) throws JwtException
    {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDateSupplier.get())
                .signWith(SECRET)
                .compact();
    }
    
    public Claims decodeToken(String token) throws JwtException
    {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    private Date getAccessTokenExpirationDate()
    {
        return getExpirationDate(AuthConstants.ACCESS_TOKEN_DURATION_MS);
    }
    
    private Date getRefreshTokenExpirationDate()
    {
        return getExpirationDate(AuthConstants.REFRESH_TOKEN_DURATION_MS);
    }
    
    private Date getExpirationDate(long millisecondsOffset)
    {
        return new Date(System.currentTimeMillis() + millisecondsOffset);
    }
}
