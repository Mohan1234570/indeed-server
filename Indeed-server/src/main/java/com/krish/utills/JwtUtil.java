package com.krish.utills;
//package com.krish.utills;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtUtil {

 // Use a secret key string with length >= 32 bytes
 private static final String SECRET = "your-256-bit-secret-your-256-bit-secret!"; // at least 32 chars

 // Convert secret string to Key using Keys.hmacShaKeyFor()
 private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

 public static String generateToken(String username) {
     return Jwts.builder()
             .setSubject(username)
             .setIssuedAt(new Date())
             .setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) // 10 hours
             .signWith(key, SignatureAlgorithm.HS256)  // key first, then alg
             .compact();
 }

 public static Key getSigningKey() {
     return key;
 }
}
