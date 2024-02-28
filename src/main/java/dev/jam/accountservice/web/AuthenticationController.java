package dev.jam.accountservice.web;


import dev.jam.accountservice.service.IAuthenticationService;
import dev.jam.accountservice.service.dtos.AuthenticationRequest;
import dev.jam.accountservice.service.dtos.AuthenticationResponse;
import dev.jam.accountservice.service.dtos.DTOUserCredentials;
import dev.jam.accountservice.service.dtos.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final IAuthenticationService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

//    @PutMapping
//    public ResponseEntity<AuthenticationResponse> updateCredentials(@RequestBody DTOUserCredentials userCredentials) {
//        return ResponseEntity.ok(authService.updateCredentials(userCredentials));
//    }
}
