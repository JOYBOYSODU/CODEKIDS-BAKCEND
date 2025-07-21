package com.codekids.codekids.services.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codekids.codekids.services.model.User;
import com.codekids.codekids.services.repository.UserRepository;

import jakarta.validation.Valid;

/**
 * This controller defines the API endpoints for user authentication.
 */
@RestController
@RequestMapping("/api/auth")
// Allow requests from your frontend application (e.g., http://localhost:4200 for Angular)
@CrossOrigin(origins = "*") 
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is already taken!");
        }

        // IMPORTANT: Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> credentials) {
        Optional<User> userOptional = userRepository.findByEmail(credentials.get("email"));

        if (userOptional.isEmpty() || !passwordEncoder.matches(credentials.get("password"), userOptional.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Invalid email or password");
        }
        
        // For a real app, you would generate and return a JWT (JSON Web Token) here.
        // For now, we return the user's profile information upon successful login.
        User user = userOptional.get();
        return ResponseEntity.ok(user);
    }
}
