package dev.jam.accountservice.service;


import dev.jam.accountservice.service.dtos.AuthenticationRequest;
import dev.jam.accountservice.service.dtos.AuthenticationResponse;
import dev.jam.accountservice.service.dtos.DTOUserCredentials;
import dev.jam.accountservice.service.dtos.RegisterRequest;

public interface IAuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);


    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse updateCredentials(DTOUserCredentials userCredentials);
}
