package dev.jam.accountservice.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.Key;

/***
 * Cette classe nous aide à obtenir les propriétés JWT du fichier application.properties,
 * afin que nous puissions modifier la configuration JWT sans entrer dans le code
 */
@Data
// Il faut ajouter l'annotation
// @EnableConfigurationProperties(JwtConfig.class) dans la class BackendApplication
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    private String accessTokenSecret;
    private long accessTokenExpiredAfter;
    private SignatureAlgorithm algorithm; // The algorithm used for signing the token

    public void setAlgorithm(String algorithm) {
        this.algorithm = SignatureAlgorithm.valueOf(algorithm);
    }

    public Key getAccessTokenSecret() {
        // Convert the secret to a byte array
        byte[] keyBytes = Decoders.BASE64.decode(accessTokenSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
