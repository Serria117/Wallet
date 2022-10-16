package com.example.wallet.utils;

import com.example.wallet.domain.user.AppUserDetail;
import com.example.wallet.domain.user.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;

@Component @Slf4j
public class JwtUtil
{
    @AllArgsConstructor @Getter @Setter
    public static class JwtToken
    {
        String token;
        Date expiredDate;
        Boolean succeed;
    }

    @Autowired UserService userService;

    @Value("${jwt.secret}")
    private String SECRET;
    @Value("${jwt.expired}")
    private Long EXPIRED_TIME;

    public JwtToken generateToken(AppUserDetail userDetail)
    {
        var now = new Date(System.currentTimeMillis());
        var expiredTime = new Date(now.getTime() + EXPIRED_TIME);
        Map<String, Object> claims = new HashMap<>();
        var userRoles = userDetail.getAuthorities()
                                  .stream()
                                  .map(GrantedAuthority::getAuthority).toArray();
        if ( userRoles.length > 0 ) {
            claims.put("roles", userRoles);
        }
        claims.put("id", userDetail.getUser().id());
        claims.put("username", userDetail.getUsername());
        var token = Jwts.builder()
                        .setSubject(userDetail.getUsername())
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(expiredTime)
                        .signWith(SignatureAlgorithm.HS512, SECRET)
                        .compact();
        return new JwtToken(token, expiredTime, true);
    }

    public JwtToken refreshToken(Claims claims, String subject)
    {
        var now = new Date(System.currentTimeMillis());
        var expiredTime = new Date(now.getTime() + EXPIRED_TIME);
        var refreshToken = Jwts.builder().setClaims(claims)
                               .setSubject(subject)
                               .setIssuedAt(now)
                               .setExpiration(expiredTime)
                               .signWith(SignatureAlgorithm.HS512, SECRET)
                               .compact();
        return new JwtToken(refreshToken, expiredTime, true);
    }

    public String getUserNameFromToken(String token)
    {
        return extractClaims(token).getSubject();
    }

    public Collection<Object> getRolesFromToken(String token)
    {
        Collection<Object> roles = null;
        Claims claims = extractClaims(token);
        Boolean isRoleExisted = claims.get("roles", Boolean.class);
        if ( isRoleExisted ) {
            roles = Collections.singletonList(claims.get("roles"));
        }
        return roles;
    }

    public Long getUserIdFromToken(String token)
    {
        try {
            return Long.parseLong((String) extractClaims(token).get("id"));
        }
        catch ( Exception e ) {
            return null;
        }
    }

    public boolean validateToken(String token)
    {
        try {
            Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token);
            return true;
        }
        catch ( ExpiredJwtException e ) {
            log.error("Token has expired");
            throw new RuntimeException("Token has expired");
        }
        catch ( Exception e ) {
            log.error("Verification of Token failed");
            throw new BadCredentialsException("Invalid token");
        }
    }

    private Claims extractClaims(String token)
    {
        return Jwts.parser()
                   .setSigningKey(SECRET)
                   .parseClaimsJws(token).getBody();
    }
}
