package dev.jam.accountservice.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * La response retourner de l'authentification
 * On va enlever le refershToken si on va utiliser qu'un seul token pour l'authentification.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String accessToken;
}
