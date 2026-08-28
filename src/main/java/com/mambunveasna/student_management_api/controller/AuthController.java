package com.mambunveasna.student_management_api.controller;

import com.mambunveasna.student_management_api.dto.AuthResponseDTO;
import com.mambunveasna.student_management_api.dto.RefreshTokenRequestDTO;
import com.mambunveasna.student_management_api.dto.RegisterRequestDTO;
import com.mambunveasna.student_management_api.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mambunveasna.student_management_api.dto.LoginRequestDTO;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }



    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequestDTO request
    ){

        String message = authService.register(request);
        return ResponseEntity.ok(message);
    }
    @PostMapping("/login")
    public ResponseEntity<
            AuthResponseDTO> login(
            @RequestBody LoginRequestDTO request
    ){
        AuthResponseDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refreshToken(
            @RequestBody RefreshTokenRequestDTO request
    ) {
        AuthResponseDTO response = authService.refreshToken(request);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(Authentication authentication) {

        String username = authentication.getName();

        authService.logout(username);

        return ResponseEntity.ok("Logout successful");
    }


}
