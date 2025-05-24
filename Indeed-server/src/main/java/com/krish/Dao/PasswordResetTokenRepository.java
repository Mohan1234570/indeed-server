//package com.krish.Dao;
//
//
//import java.util.Optional;
//
//import org.springframework.data.mongodb.repository.MongoRepository;
//
//import com.krish.utills.PasswordResetToken;
//
//public interface PasswordResetTokenRepository extends MongoRepository<PasswordResetToken, String> {
//    Optional<PasswordResetToken> findByTokenHash(String tokenHash);
//    
//}
//



package com.krish.Dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krish.utills.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByTokenHash(String tokenHash);
}
