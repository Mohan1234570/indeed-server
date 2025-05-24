//package com.krish.Dao;
//
//
//import com.krish.model.User;
//import org.springframework.data.mongodb.repository.MongoRepository;
//import java.util.Optional;
//
//public interface UserRepository extends MongoRepository<User, String> {
//    Optional<User> findByEmail(String email);
//    boolean existsByEmail(String email);
//}



package com.krish.Dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krish.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
