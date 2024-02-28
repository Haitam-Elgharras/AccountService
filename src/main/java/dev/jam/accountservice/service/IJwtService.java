package dev.jam.accountservice.service;


import dev.jam.accountservice.dao.entities.UserAccount;
import dev.jam.accountservice.enumerations.Role;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.Map;

public interface IJwtService {
    String extractUsername(String token);

    Long getUserId(String token);

    Role getUserRole(String token);

    String generateToken(UserAccount userAccount);

    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    String generateToken(Map<String, Object> extraClaims, String subject, Key secret, long expiredAfter);

    String generateAccessToken(UserAccount userAccount);

    UserAccount getUserFromToken(String jwt);

    // Helper methode to generate access and refresh tokens
    // to avoid redundancy in code
    //    AuthenticationResponse generateTokens(User user);

    UserAccount getAuthenticatedUser();

    // validate token
    Boolean isTokenValid(String token, UserDetails userDetails);

    boolean isTokenExpired(String token);

    Date extractExpiration(String token);
}
