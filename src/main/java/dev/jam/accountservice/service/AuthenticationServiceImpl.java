package dev.jam.accountservice.service;


import dev.jam.accountservice.security.IAuthenticationService;
import dev.jam.accountservice.service.dtos.AuthenticationRequest;
import dev.jam.accountservice.service.dtos.AuthenticationResponse;
import dev.jam.accountservice.service.dtos.DTOUserCredentials;
import io.jsonwebtoken.lang.Strings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j

public class AuthenticationServiceImpl {
    private final IAuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PutMapping
    public ResponseEntity<AuthenticationResponse> updateCredentials(@RequestBody DTOUserCredentials userCredentials) {
        return ResponseEntity.ok(authenticationService.updateCredentials(userCredentials));
    }
}
