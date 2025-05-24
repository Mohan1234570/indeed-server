package com.krish.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.krish.Dao.UserRepository;
import com.krish.model.User;
import com.krish.utills.JwtUtil;
import java.util.Optional;



@Service
public class AuthService {
	
	private static final Logger log = LoggerFactory.getLogger(AuthService.class);


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        userRepository.save(user);
        return "Registered Successfully!";
    }

    public String login(String email, String password) {
        email = email.trim().toLowerCase();  // normalize email
        Optional<User> userOpt = userRepository.findByEmail(email);
        
        if (userOpt.isEmpty()) {
            log.error("Login failed: user not found for email {}", email);
            throw new RuntimeException("Invalid email or password!");
        }
        
        User user = userOpt.get();
        
        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.error("Login failed: password mismatch for user {}", email);
            throw new RuntimeException("Invalid email or password!");
        }

        return JwtUtil.generateToken(email);
    }

}
