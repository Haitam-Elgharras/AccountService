package dev.jam.accountservice.service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Utiliser pour la modification des informations d'authentification
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOUserCredentials {
    private String email;
    private String oldPassword;
    private String password;
}