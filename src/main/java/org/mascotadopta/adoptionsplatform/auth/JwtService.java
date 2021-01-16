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
    /**
     * Key to sign the JWTs with.
     */
    private final SecretKey SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    /**
     * Generates an access token for the User associated with <code>email</code>. Its expiration date is determined in
     * AuthConstants.
     *
     * @param email The email of the User.
     * @return A JWT that serves as an access token for the User associated with <code>email</code>.
     */
    public String generateAccessToken(String email)
    {
        return generateToken(email, this::getAccessTokenExpirationDate);
    }
    
    /**
     * Generates a refresh token for the User associated with <code>email</code>. Its expiration date is determined in
     * AuthConstants.
     *
     * @param email The email of the User.
     * @return A JWT that serves as a refresh token for the User associated with <code>email</code>.
     */
    public String generateRefreshToken(String email)
    {
        return generateToken(email, this::getRefreshTokenExpirationDate);
    }
    
    /**
     * Generates a JWT for the User associated with <code>email</code>. Its expiration date is determined by the
     * supplier <code>expirationDateSupplier</code>.
     *
     * @param email                  The email of the User.
     * @param expirationDateSupplier A function that returns the expiration date of the token.
     * @return A JWT for the User associated with <code>email</code>. Its expiration date is determined by the supplier.
     * @throws JwtException If an error occurs while generating the JWT.
     */
    private String generateToken(String email, Supplier<Date> expirationDateSupplier) throws JwtException
    {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expirationDateSupplier.get())
                .signWith(SECRET)
                .compact();
    }
    
    /**
     * Parses a JWT into its claims.
     *
     * @param token JWT to parse.
     * @return The claims of the JWT provided.
     * @throws JwtException If an error occurs while parsing the JWT. This includes it being malformed, invalid or
     *                      expired.
     */
    public Claims decodeToken(String token) throws JwtException
    {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    /**
     * Generates the date when an access token that is being generated will expire.
     *
     * @return The <code>Date</code> when the access token that is being generated will expire.
     */
    private Date getAccessTokenExpirationDate()
    {
        return getFutureDate(AuthConstants.ACCESS_TOKEN_DURATION_MS);
    }
    
    /**
     * Generates the date when a refresh token that is being generated will expire.
     *
     * @return The <code>Date</code> when the refresh token that is being generated will expire.
     */
    private Date getRefreshTokenExpirationDate()
    {
        return getFutureDate(AuthConstants.REFRESH_TOKEN_DURATION_MS);
    }
    
    /**
     * Generates a <code>Date</code> representing the timestamp of an offset relative to the current moment. In other
     * words, at the time this method is called, it will return a Date after the offset has elapsed.
     *
     * @param millisecondsOffset The offset in milliseconds.
     * @return A <code>Date</code> that represents a future timestamp after <code>millisecondsOffset</code>.
     */
    private Date getFutureDate(long millisecondsOffset)
    {
        return new Date(System.currentTimeMillis() + millisecondsOffset);
    }
}
