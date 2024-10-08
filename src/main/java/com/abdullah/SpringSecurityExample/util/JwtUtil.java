package com.abdullah.SpringSecurityExample.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    private final SecretKey SECRET_KEY = getKey();

    public String getToken(String email,String authorities){
        return Jwts.builder()
                .subject(email)
                .claim("authorities",authorities)
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

    public String getAuthorities(String token){
        Claims claims = getClaims(token);
        return (String) claims.get("authorities");
    }

    public boolean isExpired(String token){
        Claims claims = getClaims(token);
        return claims.getExpiration().before(new Date());
    }

    private Claims getClaims(String token){
        try{
            JwtParser parser = Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build();
            return parser.parseSignedClaims(token).getPayload();
        }
        catch (SignatureException e){
            throw new SignatureException("Token signature invalid.");
        }
        catch (Exception e){
            throw new RuntimeException("Token could not be parsed: "+e.getMessage());
        }
    }

    private SecretKey getKey(){
        try{
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            keyGenerator.init(256);
            return keyGenerator.generateKey();
        }
        catch (Exception e){
            throw new RuntimeException("Something went wrong while generating crypto key.");
        }
    }
}
