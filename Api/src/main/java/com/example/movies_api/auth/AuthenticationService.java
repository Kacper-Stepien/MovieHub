package com.example.movies_api.auth;

import com.example.movies_api.config.JwtService;
import com.example.movies_api.events.MediatorConfig;
import com.example.movies_api.exception.BadRequestException;
import com.example.movies_api.exception.ResourceNotFoundException;
import com.example.movies_api.model.User;
import com.example.movies_api.model.UserRole;
import com.example.movies_api.repository.RoleRepository;
import com.example.movies_api.repository.UserRepository;
import com.example.movies_api.state.session.AdminUserState;
import com.example.movies_api.state.session.GuestUserState;
import com.example.movies_api.state.session.LoggedInUserState;
import com.example.movies_api.events.EventType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;            // Usługa generująca tokeny JWT dla użytkowników.
    private final AuthenticationManager authenticationManager;  // Obsługuje proces uwierzytelniania użytkownika na podstawie loginu (email) i hasła.
    private final RoleRepository roleRepository;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new BadRequestException("Użytkownik o podanym adresie e-mail już istnieje");
        }

        UserRole userRole = roleRepository.findByName("USER").orElseGet(() -> {
            UserRole newUserRole = new UserRole("USER");
            return roleRepository.save(newUserRole);
        });

        var user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(Set.of(userRole))
                .build();
        userRepository.save(user);
        MediatorConfig.MEDIATOR.notify(this, EventType.NEW_ACCOUNT_CREATED);
        return AuthenticationResponse.builder().build();
    }

public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
    try {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        var user = userRepository.findByEmail(authenticationRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Użytkownik nie istnieje"));
        var jwtToken = jwtService.generateToken(user);
        assignSessionState(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRoles().stream().findFirst().get().getName())
                .build();
    } catch (BadCredentialsException e) {
        throw new BadRequestException("Nieprawidłowy email lub hasło");
    }
}

public void assignSessionState(User user) {
    Set<String> roleNames = user.getRoles().stream()
        .map(UserRole::getName)
        .collect(Collectors.toSet());

    if (roleNames.contains("ADMIN")) {
        user.setSessionState(new AdminUserState());
    } else if (roleNames.contains("USER")) {
        user.setSessionState(new LoggedInUserState());
    } else {
        user.setSessionState(new GuestUserState());
    }
}

}

