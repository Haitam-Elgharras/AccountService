package dev.jam.accountservice.service;

import dev.jam.accountservice.config.JwtConfig;
import dev.jam.accountservice.dao.entities.UserAccount;
import dev.jam.accountservice.enumerations.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService implements IJwtService {
    private final JwtConfig jwtConfig;

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Long getUserId(String token) {
        return Long.parseLong(extractAllClaims(token).get("id").toString());
    }

    @Override
    public Role getUserRole(String token) {
        return Role.valueOf(extractAllClaims(token).get("role").toString());
    }

    @Override
    public String generateToken(UserAccount userAccount) {
        return generateToken(Map.of("role", userAccount.getRole(), "id", userAccount.getId(),
                "email", userAccount.getEmail(),"name", userAccount.getName()), userAccount);
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){

        return Jwts
                .builder()
                .addClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis())) // when this claim was created
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getAccessTokenExpiredAfter()))
                .signWith(jwtConfig.getSignInKey(), SignatureAlgorithm.HS256)
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

    @Override
    public String generateAccessToken(UserAccount userAccount) {
        return generateToken(
                Map.of("role", userAccount.getRole(),
                        "id", userAccount.getId(),
                        "email", userAccount.getEmail()),
                userAccount.getEmail(),
                jwtConfig.getSignInKey(),
                jwtConfig.getAccessTokenExpiredAfter()
        );
    }

    @Override
    public UserAccount getUserFromToken(String jwt) {
        UserAccount userAccount = new UserAccount();
        Claims claims = extractAllClaims(jwt);
        userAccount.setId(Long.parseLong(claims.get("id").toString()));
        userAccount.setName(claims.getSubject());
        userAccount.setRole(Role.valueOf(claims.get("role").toString()));
        return userAccount;
    }

    @Override
    public UserAccount getAuthenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken){
            return (UserAccount) authentication.getPrincipal();
        }
        return null;
    }

    // Helper method to get access token claims
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtConfig.getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // extract a specific claim from the token
    public <T> T extractClaim(String token, Function<Claims,T> ClaimsResolver){
        final Claims claims = extractAllClaims(token);
        return ClaimsResolver.apply(claims);
    }

    // validate token
    @Override
    public Boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public boolean isTokenExpired(String token) {
        assert extractExpiration(token) != null;
        return extractExpiration(token).before(new Date());
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
