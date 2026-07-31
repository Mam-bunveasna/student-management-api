package com.mambunveasna.student_management_api.service;

import com.mambunveasna.student_management_api.dto.LoginRequestDTO;
import com.mambunveasna.student_management_api.dto.RegisterRequestDTO;
import com.mambunveasna.student_management_api.model.User;
import com.mambunveasna.student_management_api.repository.UserRepository;
import com.mambunveasna.student_management_api.security.CustomUserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;


    // Constructor Injection
    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {

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

        user.setRole(request.getRole());  // ✅ set first

        userRepository.save(user);        // ✅ save after

        return "User registered successfully";
    }

    public String login(LoginRequestDTO request) {

        System.out.println("LOGIN START");

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            System.out.println("LOGIN SUCCESS");
        } catch (Exception e) {
            System.out.println("EXCEPTION = " + e.getClass().getName());
            System.out.println("MESSAGE = " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow();

        return jwtService.generateToken(new CustomUserDetails(user));
    }
}