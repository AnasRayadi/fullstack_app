package com.rayadi.backend.controller;

import com.rayadi.backend.dto.RefreshRequest;
import com.rayadi.backend.dto.SignInDto;
import com.rayadi.backend.dto.SignUpDto;
import com.rayadi.backend.model.AuthResponse;
import com.rayadi.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody SignUpDto request){
        return ResponseEntity.ok(authService.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody SignInDto request){
        return ResponseEntity.ok(authService.authenticate(request));
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshRequest request){
        return ResponseEntity.ok(authService.refreshToken(request));
    }

}
