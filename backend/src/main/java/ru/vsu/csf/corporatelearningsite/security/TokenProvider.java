package ru.vsu.csf.corporatelearningsite.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import ru.vsu.csf.corporatelearningsite.config.AppProperties;
import ru.vsu.csf.corporatelearningsite.security.user.UserPrincipal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    public static final String INVALID_JWT_SIGNATURE_LOGGER_MESSAGE = "Invalid JWT signature";
    public static final String INVALID_JWT_TOKEN_LOGGER_MESSAGE = "Invalid JWT token";
    public static final String EXPIRED_JWT_TOKEN_LOGGER_MESSAGE = "Expired JWT token";
    public static final String UNSUPPORTED_JWT_TOKEN_LOGGER_MESSAGE = "Unsupported JWT token";
    public static final String JWT_CLAIMS_STRING_IS_EMPTY_LOGGER_MESSAGE = "JWT claims string is empty";
    public static final String AUTHORITIES_DELIMITER = ",";
    public static final String AUTHORITIES_KEY = "Authorities";

    private final AppProperties appProperties;

    @Autowired
    public TokenProvider(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(AUTHORITIES_DELIMITER));
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());

        return Jwts.builder()
                .setSubject(userPrincipal.getId().toString())
                .claim(AUTHORITIES_KEY, authorities)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
                .compact();
    }

    public UUID getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(appProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();

        return UUID.fromString(claims.getSubject());
    }

    public Collection<GrantedAuthority> getAuthorities(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(appProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();
        return Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(AUTHORITIES_DELIMITER))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(appProperties.getAuth().getTokenSecret())
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            logger.error(INVALID_JWT_SIGNATURE_LOGGER_MESSAGE, e);
        } catch (MalformedJwtException e) {
            logger.error(INVALID_JWT_TOKEN_LOGGER_MESSAGE, e);
        } catch (ExpiredJwtException e) {
            logger.error(EXPIRED_JWT_TOKEN_LOGGER_MESSAGE, e);
        } catch (UnsupportedJwtException e) {
            logger.error(UNSUPPORTED_JWT_TOKEN_LOGGER_MESSAGE, e);
        } catch (IllegalArgumentException e) {
            logger.error(JWT_CLAIMS_STRING_IS_EMPTY_LOGGER_MESSAGE, e);
        }
        return false;
    }
}
