package org.mascotadopta.adoptionsplatform.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * JSON Web Tokens related functionality.
 */
@Service
public class JwtService
{
    private final SecretKey SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    private final long ACCESS_TOKEN_DURATION_MS = TimeUnit.MINUTES.toMillis(10);
    
    private final long REFRESH_TOKEN_DURATION_MS = TimeUnit.DAYS.toMillis(14);
    
    public String generateAccessToken(String email)
    {
        return generateToken(email, this::getAccessTokenExpirationDate);
    }
    
    public String generateRefreshToken(String email)
    {
        return generateToken(email, this::getRefreshTokenExpirationDate);
    }
    
    private String generateToken(String email, Supplier<Date> expirationDateSupplier)
    {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDateSupplier.get())
                .signWith(SECRET)
                .compact();
    }
    
    public Claims decodeToken(String token)
    {
        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(SECRET)
                .build()
                .parseClaimsJws(token);
        
        Claims claims = jws.getBody();
        Date expirationDate = claims.getExpiration();
        Date now = new Date();
        
        if (now.after(expirationDate)) throw new ExpiredJwtException(jws.getHeader(), claims, "Expired JWT");
        
        return claims;
    }
    
    private Date getAccessTokenExpirationDate()
    {
        return getExpirationDate(ACCESS_TOKEN_DURATION_MS);
    }
    
    private Date getRefreshTokenExpirationDate()
    {
        return getExpirationDate(REFRESH_TOKEN_DURATION_MS);
    }
    
    private Date getExpirationDate(long millisecondsOffset)
    {
        return new Date(System.currentTimeMillis() + millisecondsOffset);
    }
}
