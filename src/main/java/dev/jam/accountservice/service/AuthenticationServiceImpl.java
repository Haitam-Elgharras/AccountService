package dev.jam.accountservice.service;


import dev.jam.accountservice.dao.entities.UserAccount;
import dev.jam.accountservice.enumerations.Role;
import dev.jam.accountservice.service.dtos.AuthenticationRequest;
import dev.jam.accountservice.service.dtos.AuthenticationResponse;
import dev.jam.accountservice.service.dtos.DTOUserCredentials;
import dev.jam.accountservice.service.dtos.RegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {
    private final IUserService userService;
    private final IJwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws BadCredentialsException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        UserAccount userAccount = userService.loadUserByUsername(request.getEmail());
        if (userAccount == null)
            throw new BadCredentialsException("Unauthorized");

        String jwtToken = jwtService.generateAccessToken(userAccount);
        return new AuthenticationResponse(jwtToken);
    }

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        UserAccount userAccount = new UserAccount();
        userAccount.setName(request.getName());
        userAccount.setEmail(request.getEmail());
        userAccount.setPassword(passwordEncoder.encode(request.getPassword()));
        userAccount.setRole(Role.ROLE_USER);
        userService.addUser(userAccount);
        String jwtToken = jwtService.generateToken(userAccount);
        return new AuthenticationResponse(jwtToken);
    }

    @Override
    public AuthenticationResponse updateCredentials(DTOUserCredentials userCredentials) {
        UserAccount authUserAccount = jwtService.getAuthenticatedUser();
        if (authUserAccount != null) {
            UserAccount userAccount = userService.loadUserByUsername(jwtService.getAuthenticatedUser().getUsername());

            boolean passwordsMatchers = (new BCryptPasswordEncoder()).matches(userCredentials.getOldPassword(), userAccount.getPassword());
            if (!passwordsMatchers)
                throw new BadCredentialsException("Unauthorized");
            //
            userAccount.setEmail(userCredentials.getEmail());
            userAccount.setPassword(userCredentials.getPassword());
            //
            String accessToken = jwtService.generateAccessToken(
                    userService.updateUser(userAccount)
            );
            return new AuthenticationResponse(accessToken);
        }
        return null;
    }
}
