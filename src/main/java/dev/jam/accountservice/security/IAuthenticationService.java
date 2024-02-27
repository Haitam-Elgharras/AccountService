package dev.jam.accountservice.security;


import dev.jam.accountservice.service.dtos.AuthenticationRequest;
import dev.jam.accountservice.service.dtos.AuthenticationResponse;
import dev.jam.accountservice.service.dtos.DTOUserCredentials;

public interface IAuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse updateCredentials(DTOUserCredentials userCredentials);
}
