package dev.jam.accountservice.security;


import dev.jam.accountservice.dao.entities.User;
import dev.jam.accountservice.service.IUserService;
import dev.jam.accountservice.service.dtos.AuthenticationRequest;
import dev.jam.accountservice.service.dtos.AuthenticationResponse;
import dev.jam.accountservice.service.dtos.DTOUserCredentials;
import io.jsonwebtoken.lang.Strings;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AuthenticationService implements IAuthenticationService {
    private final IUserService userService;
    private final IJwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(IUserService userService, IJwtService jwtService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws BadCredentialsException {
        // Authenticating user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userService.loadUserByUsername(request.getEmail());
        String accessToken = jwtService.generateAccessToken(user);
        return new AuthenticationResponse(accessToken);
    }

    @Transactional
    @Override
    public AuthenticationResponse updateCredentials(DTOUserCredentials userCredentials) {
        User authUser = jwtService.getAuthenticatedUser();
        if (authUser != null) {
            User user = userService.loadUserByUsername(jwtService.getAuthenticatedUser().getUsername());
            // Verification de l'ancien mot de passe envoyé par l'utilisateur
            boolean passwordsMatchers = (new BCryptPasswordEncoder()).matches(userCredentials.getOldPassword(), user.getPassword());
            if (!passwordsMatchers)
                throw new BadCredentialsException("Unauthorized");
            //
            user.setEmail(userCredentials.getEmail());
            user.setPassword(userCredentials.getPassword());
            //
            String accessToken = jwtService.generateAccessToken(
                    userService.updateUser(user)
            );
            return new AuthenticationResponse(accessToken);
        }
        return null;
    }
}
