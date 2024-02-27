package dev.jam.accountservice.security;

import dev.jam.accountservice.config.JwtConfig;
import dev.jam.accountservice.dao.entities.User;
import dev.jam.accountservice.enumerations.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService implements IJwtService {
    private final JwtConfig jwtConfig;

    @Override
    public String getUsername(String token) {
        return getAccessTokenClaims(token).getSubject();
    }

    @Override
    public Long getUserId(String token) {
        return Long.parseLong(getAccessTokenClaims(token).get("id").toString());
    }

    @Override
    public Role getUserRole(String token) {
        return Role.valueOf(getAccessTokenClaims(token).get("role").toString());
    }

    @Override
    public String generateToken(User user) {
        return generateToken(Map.of("role", user.getRole(), "id", user.getId(), "name", user.getEmail()), user);
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims, User user) {

        return Jwts
                .builder()
                .addClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getAccessTokenExpiredAfter()))
                .signWith(jwtConfig.getAccessTokenSecret(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims, String subject, Key secret, long expiredAfter) {
        return Jwts
                .builder()
                .addClaims(extraClaims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredAfter))
                .signWith(secret, jwtConfig.getAlgorithm())
                .compact();
    }

//    @Override
//    public String generateAccessToken(String refreshToken) {
//        String username = getRefreshTokenClaims(refreshToken).getSubject();
//        User user = userService.loadUserByUsername(username);
//        return generateAccessToken(user);
//    }

    @Override
    public String generateAccessToken(User user) {
        return generateToken(
                Map.of("role", user.getRole(), "id", user.getId(), "email", user.getEmail()),
                user.getEmail(),
                jwtConfig.getAccessTokenSecret(),
                jwtConfig.getAccessTokenExpiredAfter()
        );
    }

    @Override
    public User getUserFromToken(String jwt) {
        User user = new User();
        Claims claims = getAccessTokenClaims(jwt);
        user.setId(Long.parseLong(claims.get("id").toString()));
        user.setName(claims.getSubject());
        user.setRole(Role.valueOf(claims.get("role").toString()));
        return user;
    }

    @Override
    public User getAuthenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken){
            return (User) authentication.getPrincipal();
        }
        return null;
    }

    // Helper method to get access token claims
    private Claims getAccessTokenClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtConfig.getAccessTokenSecret())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
