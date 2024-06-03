package com.rayadi.backend.service;

import com.rayadi.backend.dto.RefreshRequest;
import com.rayadi.backend.dto.SignInDto;
import com.rayadi.backend.dto.SignUpDto;
import com.rayadi.backend.model.AuthResponse;
import com.rayadi.backend.enums.Role;
import com.rayadi.backend.model.User;
import com.rayadi.backend.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(SignUpDto request) {
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepo.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthResponse
                .builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
    public AuthResponse authenticate(SignInDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword())
        );
        var user = userRepo.findByUsername(request.getUsername()).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthResponse
                .builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
    public AuthResponse refreshToken(RefreshRequest request) {
        final String username = jwtService.extractUsername(request.getRefreshToken());
        var user = userRepo.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        if (jwtService.tokenIsValid(request.getRefreshToken(),user)){
            var jwtToken = jwtService.generateToken(user);
            //var refreshToken = jwtService.generateRefreshToken(user);
            return AuthResponse
                    .builder()
                    .accessToken(jwtToken)
                    .refreshToken(request.getRefreshToken())
                    .build();
        }
        throw new RuntimeException("Refresh token is not valid");
    }

}
