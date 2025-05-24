package com.krish.rest;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krish.dto.ApiResponse;
import com.krish.dto.UserDto;
import com.krish.model.User;
import com.krish.service.AuthService;
import com.krish.service.PasswordResetService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    
    private final PasswordResetService passwordResetService;
    
    @Autowired
    public AuthController(AuthService authService, PasswordResetService passwordResetService) {
        this.authService = authService;
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody UserDto userDto) {
        try {
            User user = new User();
            user.setUsername(userDto.getName());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());

            authService.register(user);
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK.value(), "Registration successful", null);
            return ResponseEntity.ok(response);

        } catch (RuntimeException ex) {
            if ("Email already registered!".equals(ex.getMessage())) {
                ApiResponse<String> response = new ApiResponse<>(HttpStatus.CONFLICT.value(), ex.getMessage(), null);
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = authService.login(loginRequest.getEmail(), loginRequest.getPassword());

            Map<String, String> data = new HashMap<>();
            data.put("token", token);
            data.put("username", loginRequest.getEmail());

            ApiResponse<Map<String, String>> response = new ApiResponse<>(HttpStatus.OK.value(), "Login successful", data);
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            ApiResponse<Map<String, String>> response = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }


    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@Valid@RequestBody ForgotPasswordRequest request) {
        try {
            String appUrl = "http://localhost:3000"; // Your frontend URL
            passwordResetService.requestPasswordReset(request.getEmail(), appUrl);

            ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK.value(), "If the email exists, password reset instructions have been sent.", null);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error processing request", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<String>> resetPassword(@Valid@RequestBody ResetPasswordRequest request) {
    	System.out.println("Reset password request token: [" + request.getToken() + "]");
        boolean success = passwordResetService.resetPassword(request.getToken(), request.getNewPassword());

        if (success) {
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK.value(), "Password reset successful", null);
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Invalid or expired token", null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Data
    static class ForgotPasswordRequest {
        @Email
        @NotBlank
        private String email;
    }

    @Data
    static class ResetPasswordRequest {
        @NotBlank
        private String token;

        @NotBlank
        private String newPassword;
    }



    @Data
    @Setter
    @Getter
    @AllArgsConstructor
    static class LoginRequest {
        private String email;
        private String password;
    }
    
    
    @PostMapping("/verify-reset-token")
    public ResponseEntity<ApiResponse<String>> verifyResetToken(@RequestBody VerifyOtpRequest request) {
        boolean verified = passwordResetService.verifyTokenAndOtp(request.getToken(), request.getOtp());
        if (verified) {
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "OTP verified, proceed to reset password", null));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Invalid or expired OTP/token", null));
        }
    }
    
    @Data
    static class VerifyOtpRequest {
        @NotBlank
        private String token;

        @NotBlank
        private String otp;
    }


}



