package com.abdullah.SpringSecurityExample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class EncoderConfig {
    @Bean
    public PasswordEncoder getEncoder(){
        try{
            return new Argon2PasswordEncoder(16,16,4,1024,16);
        }
        catch (Exception e){
            throw new RuntimeException("Failed to get password encoder: "+e.getMessage());
        }
    }
}
