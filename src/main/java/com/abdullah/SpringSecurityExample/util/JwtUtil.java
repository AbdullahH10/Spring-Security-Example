package com.abdullah.SpringSecurityExample.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    private final SecretKey SECRET_KEY = getKey();

    public String getToken(String email,String data){
        return Jwts.builder()
                .subject(email)
                .claim("data",data)
                .issuer("Spring Security App")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+(1000*60*10)))
                .signWith(SECRET_KEY)
                .compact();
    }
    public boolean isValid(String token){
        return getEmail(token) != null && !isExpired(token);
    }

    public String getEmail(String token){
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public String getData(String token){
        Claims claims = getClaims(token);
        return (String) claims.get("data");
    }

    public boolean isExpired(String token){
        Claims claims = getClaims(token);
        return claims.getExpiration().before(new Date());
    }

    private Claims getClaims(String token){
        JwtParser parser = Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build();
        return (Claims) parser.parseSignedClaims(token);
    }

    private SecretKey getKey(){
        try{
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HMAC");
            keyGenerator.init(256);
            return keyGenerator.generateKey();
        }
        catch (Exception e){
            throw new RuntimeException("Something went wrong while generating crypto key.");
        }
    }
}
