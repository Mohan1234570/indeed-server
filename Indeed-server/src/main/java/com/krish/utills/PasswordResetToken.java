//package com.krish.utills;
//
//
//import java.time.Instant;
//
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//import lombok.Data;
//import lombok.Setter;
//
//@Setter
//@Data
//@Document(collection = "passwordResetTokens")
//public class PasswordResetToken {
//    @Id
//    private String id;
//
//    private String userId;
//
//    private String tokenHash;
//
//    private Instant createdAt;
//
//    private Instant expiresAt;
//
//    private boolean used;
//    
//    private String otp;
//    private Instant otpExpiresAt;
//    private boolean otpVerified = false;
//
//}
//


package com.krish.utills;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.Setter;

@Entity
@Table(name = "password_reset_tokens")
@Setter
@Data
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String tokenHash;
    private Instant createdAt;
    private Instant expiresAt;
    private boolean used;

    private String otp;
    private Instant otpExpiresAt;
    private boolean otpVerified = false;
}
