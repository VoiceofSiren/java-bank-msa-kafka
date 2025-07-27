package org.example.apigateway.infrastructure.configuration.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {

    private SecretKey secretKey;

    public JwtUtils(@Value("${jwt.secret}") String secret) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String issueJwt(String category, String username, String role, Long expirationTimeMillis) {
        return Jwts.builder()
                .claim("category", category)
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTimeMillis))
                .signWith(secretKey)
                .compact();
    }

    public Claims extractPayload(String token) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Token is null or blank");
        }

        try {
            return Jwts
                    // secretKey를 이용하여 파싱
                    .parser().verifyWith(secretKey)
                    // token 값을 이용
                    .build().parseSignedClaims(token)
                    // payload 추출
                    .getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String getCategoryFrom(String token) {
        return extractPayload(token).get("category", String.class);
    }

    public String getUsernameFrom(String token) {
        return extractPayload(token).get("username", String.class);
    }

    public String getRoleFrom(String token) {
        return extractPayload(token).get("role", String.class);
    }

    public Boolean expires(String token) {
        return extractPayload(token).getExpiration().before(new Date());
    }

    public Boolean isValid(String token) {
        return (getCategoryFrom(token) != null && getUsernameFrom(token) != null && getRoleFrom(token) != null
        && !expires(token));
    }

}
