package com.dorovidal.medical_system.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

@Component
public class TokenProvider {
    public static final String AUTORITIES_KEY = "auth";
    private final String secret = "QJeKx+s7XIv1WbBlj7vJ9CD3Ozj1rB3qjlNZY9ofWKJSaBNBo5r1q9Rru/OWlYb+UHV1n4/LJl1OBYYZZ7rhJEnn5peyHCd+eLJfRdArE37pc+QDIsJlabQtR7tYRa+SnvGRyL01uZsK33+gezV+/GPXBnPTj8fOojDUzJiPAvE=";
    private final Key key;
    private final JwtParser jwtParser;
    private final long tokenValidity = 20 * 60000; //20min de validez

    public TokenProvider() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
    }

    public String createToken(Authentication authentication) {
        String authority = authentication.getAuthorities()
                .iterator()
                .next()
                .getAuthority();
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidity);
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTORITIES_KEY, authority)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        String authority = claims.get(AUTORITIES_KEY).toString();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        User user = new User(claims.getSubject(), "", Collections.singletonList(simpleGrantedAuthority));

        return new UsernamePasswordAuthenticationToken(user, token, Collections.singletonList(simpleGrantedAuthority));
    }

    public boolean validateToken(String authToken) {
        try{
            jwtParser.parseClaimsJws(authToken);

            return true;
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(e.getHeader(), e.getClaims(), e.getMessage());
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException ignored) {}

        return false;
    }
}
