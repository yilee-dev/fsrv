package yilee.fsrv.login.helper;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import yilee.fsrv.login.exception.CustomJwtException;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {
    private static KeyPair keyPair = createKeyPair();

    public static KeyPair createKeyPair() {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            return kpg.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateToken(Map<String, Object> claims, int min) {
        return Jwts.builder()
                .signWith(keyPair.getPrivate(), SignatureAlgorithm.RS256)
                .claims(claims)
                .issuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .expiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .compact();
    }

    public static Map<String, Object> validateToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(keyPair.getPublic())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (MalformedJwtException malformedJwtException) {
            throw new CustomJwtException(malformedJwtException.getMessage());
        } catch (ExpiredJwtException expiredJwtException) {
            throw new CustomJwtException(expiredJwtException.getMessage());
        } catch (InvalidClaimException invalidClaimException) {
            throw new CustomJwtException(invalidClaimException.getMessage());
        } catch (JwtException jwtException) {
            throw new CustomJwtException(jwtException.getMessage());
        } catch (Exception e) {
            throw new CustomJwtException(e.getMessage());
        }
    }

    public static boolean isExpired(String token) {
        try {
            Jwts.parser()
                    .verifyWith(keyPair.getPublic())
                    .build()
                    .parseSignedClaims(token);
            return false;
        } catch (ExpiredJwtException e) {
            return true;
        }
    }
}