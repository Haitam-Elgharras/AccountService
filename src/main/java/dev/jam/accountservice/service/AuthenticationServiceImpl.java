package dev.jam.accountservice.service;


import dev.jam.accountservice.dao.entities.User;
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
import org.springframework.transaction.annotation.Transactional;

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
        User user = userService.loadUserByUsername(request.getEmail());
        if (user == null)
            throw new BadCredentialsException("Unauthorized");

        String jwtToken = jwtService.generateAccessToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_USER);
        userService.addUser(user);
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    @Override
    public AuthenticationResponse updateCredentials(DTOUserCredentials userCredentials) {
        User authUser = jwtService.getAuthenticatedUser();
        if (authUser != null) {
            User user = userService.loadUserByUsername(jwtService.getAuthenticatedUser().getUsername());

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
