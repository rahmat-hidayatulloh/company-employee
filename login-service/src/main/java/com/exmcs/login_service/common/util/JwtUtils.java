package com.exmcs.login_service.common.util;

import com.exmcs.login_service.service.auth.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${configuration.jwt.secret-key.value}")
    private String secretKey;

    @Value("${configuration.jwt.token-expired-time.value}")
    private long jwtExpirationMs;

    @Value("${configuration.jwt.CookieName.value}")
    private String jwtCookie;

    @Value("${spring.h2.console.path}")
    private String path;

    public String getJwtFromCookies(HttpServletRequest request) {

        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        return cookie != null ? cookie.getValue() : null;
    }

    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {

        String jwt = generateTokenFromUsername(userPrincipal.getUsername());
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt)
                .path(path)
                .maxAge(24 * 60 * 60)
                .httpOnly(true).build();
        return cookie;

    }

    public ResponseCookie getCleanJwtCookie() {

        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path(path).build();
        return cookie;
    }

    public String getUsernameFromJwtToken(String token) {

        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {

        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;

        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }


    private String generateTokenFromUsername(String username) {

        return Jwts.builder().setSubject(username).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(getKey(secretKey), SignatureAlgorithm.HS512).compact();

    }

    private static SecretKey getKey(String secretKey) {
        byte[] decodedKey = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public String generateTokenAccess(UserDetailsImpl userPrinciple) {

        return generateTokenFromUsername(userPrinciple.getUsername());
    }


}
