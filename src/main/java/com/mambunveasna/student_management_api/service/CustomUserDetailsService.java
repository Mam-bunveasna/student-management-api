package com.mambunveasna.student_management_api.service;

import com.mambunveasna.student_management_api.repository.UserRepository;
import com.mambunveasna.student_management_api.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.mambunveasna.student_management_api.model.User;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        return new CustomUserDetails(user);
    }
    private final UserRepository userRepository;
    CustomUserDetailsService (UserRepository userRepository){
       this.userRepository = userRepository;
    }

}

