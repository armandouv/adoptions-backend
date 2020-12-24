package org.mascotadopta.adoptionsplatform.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.mascotadopta.adoptionsplatform.users.User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * JSON Web Tokens related functionality.
 */
@Service
public class JwtService
{
    private final SecretKey SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    public String generateToken(User user, Date expiresAt)
    {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(expiresAt)
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
}
