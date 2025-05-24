package com.krish.service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.krish.Dao.PasswordResetTokenRepository;
import com.krish.Dao.UserRepository;
import com.krish.emailUtils.EmailService;
import com.krish.model.User;
import com.krish.utills.PasswordResetToken;
import com.krish.utills.TokenUtils;

@Service
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final long EXPIRATION_MINUTES = 15;

    public PasswordResetService(UserRepository userRepository,
                                 PasswordResetTokenRepository tokenRepository,
                                 EmailService emailService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    private String generateToken() {
        byte[] randomBytes = new byte[24];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
    public void requestPasswordReset(String email, String appUrl) throws Exception {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String token = generateToken();
            String tokenHash = TokenUtils.hashToken(token);

            // 🔐 Generate 6-digit OTP
            String otp = String.format("%06d", new SecureRandom().nextInt(999999));
            Instant now = Instant.now();
            Instant expiresAt = now.plusSeconds(EXPIRATION_MINUTES * 60);
            Instant otpExpiresAt = now.plusSeconds(5 * 60); // OTP valid for 5 minutes

            // 🧾 Save all details
            PasswordResetToken resetToken = new PasswordResetToken();
            resetToken.setUserId(user.getId());
            resetToken.setTokenHash(tokenHash);
            resetToken.setCreatedAt(now);
            resetToken.setExpiresAt(expiresAt);
            resetToken.setOtp(otp);
            resetToken.setOtpExpiresAt(otpExpiresAt);
            resetToken.setUsed(false);

            tokenRepository.save(resetToken);

            // 🌐 Create reset link
            String resetLink = appUrl + "/reset-password?token=" + token;

            // ✉️ Compose email with OTP
            String emailBody = "<p>Hello,</p>"
                    + "<p>You requested a password reset.</p>"
                    + "<p>Your OTP is:</p><h2>" + otp + "</h2>"
                    + "<p>This OTP is valid for 5 minutes.</p>"
                    + "<p>Alternatively, click the link to reset your password:</p>"
                    + "<a href=\"" + resetLink + "\">Reset Password</a>";

            // ✅ Send email
            emailService.sendEmail(user.getEmail(), "Your Password Reset OTP", emailBody);

            System.out.println("Token and OTP saved to DB for user ID: " + user.getId());
            System.out.println("Reset link: " + resetLink);
            System.out.println("OTP: " + otp);
        } else {
            System.out.println("No user found with email: " + email);
        }
    }

    public Optional<User> validatePasswordResetToken(String token) {
        String hashedToken = TokenUtils.hashToken(token);
        System.out.println("Validating token. Raw: " + token);
        System.out.println("Hashed token to lookup: " + hashedToken);

        Optional<PasswordResetToken> tokenOpt = tokenRepository.findByTokenHash(hashedToken);
        if (tokenOpt.isPresent()) {
            PasswordResetToken t = tokenOpt.get();
            if (!t.isUsed() && t.getExpiresAt().isAfter(Instant.now())) {
                System.out.println("Token is valid and not expired.");
                return userRepository.findById(t.getUserId());
            } else {
                System.out.println("Token is used or expired.");
            }
        } else {
            System.out.println("Token not found in database.");
        }

        return Optional.empty();
    }

    public boolean resetPassword(String token, String newPassword) {
        System.out.println("Attempting to reset password with token: " + token);
        String hashedToken = TokenUtils.hashToken(token);
        System.out.println("Hashed token used for lookup: " + hashedToken);

        Optional<PasswordResetToken> tokenOpt = tokenRepository.findByTokenHash(hashedToken);
        System.out.println("Token found? " + tokenOpt.isPresent());
        if (tokenOpt.isEmpty()) {
            System.out.println("Reset token not found in DB.");
            return false;
        }

        PasswordResetToken t = tokenOpt.get();
        System.out.println("Token found. Used: " + t.isUsed() + ", Expires At: " + t.getExpiresAt());

        if (t.isUsed() || t.getExpiresAt().isBefore(Instant.now())) {
            System.out.println("Reset token expired or already used.");
            return false;
        }

        Optional<User> userOpt = userRepository.findById(t.getUserId());
        if (userOpt.isEmpty()) {
            System.out.println("User not found for token.");
            return false;
        }

        User user = userOpt.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        t.setUsed(true);
        tokenRepository.save(t);

        System.out.println("Password reset successful for user: " + user.getEmail());
        return true;
    }
    
    public boolean verifyTokenAndOtp(String rawToken, String otp) {
        String tokenHash = TokenUtils.hashToken(rawToken);

        Optional<PasswordResetToken> tokenOpt = tokenRepository.findByTokenHash(tokenHash);

        if (tokenOpt.isEmpty()) {
            throw new RuntimeException("Invalid token.");
        }

        PasswordResetToken token = tokenOpt.get();

        if (token.isUsed() || token.getExpiresAt().isBefore(Instant.now())) {
            throw new RuntimeException("Token is expired or already used.");
        }

        if (!token.getOtp().equals(otp)) {
            throw new RuntimeException("Invalid OTP.");
        }

        if (token.getOtpExpiresAt().isBefore(Instant.now())) {
            throw new RuntimeException("OTP expired.");
        }

        // ✅ Mark OTP as verified
        token.setOtpVerified(true);
        tokenRepository.save(token);
        return true;
    }



}
