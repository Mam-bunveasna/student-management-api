package com.mambunveasna.student_management_api.service;

import com.mambunveasna.student_management_api.dto.LoginRequestDTO;
import com.mambunveasna.student_management_api.dto.RefreshTokenRequestDTO;
import com.mambunveasna.student_management_api.dto.RegisterRequestDTO;
import com.mambunveasna.student_management_api.exception.RefreshTokenException;
import com.mambunveasna.student_management_api.model.User;
import com.mambunveasna.student_management_api.repository.RefreshTokenRepository;
import com.mambunveasna.student_management_api.repository.UserRepository;
import com.mambunveasna.student_management_api.security.CustomUserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.mambunveasna.student_management_api.dto.AuthResponseDTO;
import com.mambunveasna.student_management_api.model.RefreshToken;

@Service
public class AuthService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;


    // Constructor Injection
    public AuthService(
            RefreshTokenRepository refreshTokenRepository, CustomUserDetailsService customUserDetailsService, UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }


    public String register(RegisterRequestDTO request) {

        User user = new User();

        user.setUsername(request.getUsername());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole("USER");  // ✅ set first

        userRepository.save(user);        // ✅ save after

        return "User registered successfully";
    }



    public AuthResponseDTO refreshToken(RefreshTokenRequestDTO request) {

        String refreshToken = request.getRefreshToken();

        RefreshToken storedToken =
                refreshTokenRepository.findByToken(refreshToken)
                        .orElseThrow(() ->
                                new RefreshTokenException(
                                        "Refresh token is invalid or has been revoked"
                                ));

        if (storedToken.getExpiryDate().isBefore(java.time.LocalDateTime.now())) {
            throw new RefreshTokenException(
                    "Refresh token has expired"
            );
        }

        String username = jwtService.extractUsername(refreshToken);

        UserDetails userDetails =
                customUserDetailsService.loadUserByUsername(username);

        if (!jwtService.isTokenValid(refreshToken, userDetails)) {
            throw new RefreshTokenException(
                    "Refresh token is invalid"
            );
        }

        String newAccessToken =
                jwtService.generateAccessToken(userDetails);

        return new AuthResponseDTO(newAccessToken, refreshToken);
    }
    public AuthResponseDTO login(LoginRequestDTO request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails =
                customUserDetailsService.loadUserByUsername(request.getUsername());

        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        RefreshToken refreshTokenEntity = new RefreshToken();

        refreshTokenEntity.setToken(refreshToken);

        refreshTokenEntity.setExpiryDate(
                jwtService.extractExpiration(refreshToken)
                        .toInstant()
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDateTime()
        );

        refreshTokenEntity.setUser(user);

        refreshTokenRepository.save(refreshTokenEntity);

        return new AuthResponseDTO(accessToken, refreshToken);
    }
    public void logout(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        refreshTokenRepository.deleteByUser(user);
    }
}