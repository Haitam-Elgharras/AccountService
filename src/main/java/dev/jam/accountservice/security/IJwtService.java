package dev.jam.accountservice.security;


import dev.jam.accountservice.dao.entities.User;
import dev.jam.accountservice.enumerations.Role;

import java.security.Key;
import java.util.Map;

public interface IJwtService {
    String getUsername(String token);

    Long getUserId(String token);

    Role getUserRole(String token);

    String generateToken(User user);

    String generateToken(Map<String, Object> extraClaims, User user);

    String generateToken(Map<String, Object> extraClaims, String subject, Key secret, long expiredAfter);

    String generateAccessToken(User user);

    User getUserFromToken(String jwt);

    // Helper methode to generate access and refresh tokens
    // to avoid redundancy in code
    //    AuthenticationResponse generateTokens(User user);

    User getAuthenticatedUser();
}
