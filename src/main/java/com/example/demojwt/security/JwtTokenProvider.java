package com.example.demojwt.security;

import io.jsonwebtoken.*;
import jdk.nashorn.internal.parser.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class JwtTokenProvider {
    private final String JWT_SECRET = "bimatchua";
    private final long JWT_EXPIRATION = 604800000L;

    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Map<String,Object> claims= new HashMap<>();
        claims.put("role",userDetails.getAuthorities());
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public String getUserNameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    List<SimpleGrantedAuthority> getRoleFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        List<LinkedHashMap> listRole = claims.get("role", List.class);
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        listRole.forEach(e -> {
            roles.add(new SimpleGrantedAuthority(e.toString().replace("{authority=","").replace("}","")));
        });
        return roles;
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("M?? th??ng b??o JWT kh??ng h???p l???");
        } catch (ExpiredJwtException ex) {
            log.error("M?? th??ng b??o JWT ???? h???t h???n");
        } catch (UnsupportedJwtException ex) {
            log.error("M?? th??ng b??o JWT kh??ng ???????c h??? tr???");
        } catch (IllegalArgumentException ex) {
            log.error("Chu???i y??u c???u JWT tr???ng.");
        }
        return false;
    }
}
