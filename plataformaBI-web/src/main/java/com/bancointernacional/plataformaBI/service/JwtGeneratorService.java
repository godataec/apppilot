package com.bancointernacional.plataformaBI.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JWT Generator Service
 * Generates JWT tokens for authenticated users
 */
@Service
public class JwtGeneratorService {

    private static final Logger logger = LogManager.getLogger(JwtGeneratorService.class);

    @Value("${jwt.secret:MySecretKeyForJWTTokenGenerationThatIsAtLeast256BitsLongForHS256Algorithm}")
    private String jwtSecret;

    @Value("${jwt.expiration:86400000}") // Default 24 hours in milliseconds
    private long jwtExpirationMs;

    @Value("${jwt.issuer:plataformaBI}")
    private String jwtIssuer;

    /**
     * Generate JWT token for a user with roles
     *
     * @param email user email (subject)
     * @param name  user name
     * @param roles user roles
     * @return JWT token string
     */
    public String generateToken(String email, String name, List<String> roles) {
        try {
            Map<String, Object> claims = new HashMap<>();
            claims.put("name", name);
            claims.put("roles", roles);
            claims.put("email", email);

            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(email)
                    .setIssuer(jwtIssuer)
                    .setIssuedAt(now)
                    .setExpiration(expiryDate)
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();

            logger.info("JWT token generated for user: {} with expiration: {}", email, expiryDate);
            return token;

        } catch (Exception e) {
            logger.error("Error generating JWT token for user: {}", email, e);
            throw new RuntimeException("Error generating JWT token", e);
        }
    }

    /**
     * Generate JWT token with custom claims
     *
     * @param email       user email (subject)
     * @param customClaims custom claims to include
     * @return JWT token string
     */
    public String generateTokenWithClaims(String email, Map<String, Object> customClaims) {
        try {
            Date now = new Date();
            Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

            String token = Jwts.builder()
                    .setClaims(customClaims)
                    .setSubject(email)
                    .setIssuer(jwtIssuer)
                    .setIssuedAt(now)
                    .setExpiration(expiryDate)
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();

            logger.info("JWT token with custom claims generated for user: {}", email);
            return token;

        } catch (Exception e) {
            logger.error("Error generating JWT token with custom claims for user: {}", email, e);
            throw new RuntimeException("Error generating JWT token", e);
        }
    }

    /**
     * Validate JWT token
     *
     * @param token JWT token to validate
     * @return true if valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Extract claims from JWT token
     *
     * @param token JWT token
     * @return claims
     */
    public Claims extractClaims(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            logger.error("Error extracting claims from token", e);
            throw new RuntimeException("Error extracting claims", e);
        }
    }

    /**
     * Extract email from JWT token
     *
     * @param token JWT token
     * @return email
     */
    public String extractEmail(String token) {
        Claims claims = extractClaims(token);
        return claims.getSubject();
    }

    /**
     * Check if token is expired
     *
     * @param token JWT token
     * @return true if expired, false otherwise
     */
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = extractClaims(token);
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }
}

