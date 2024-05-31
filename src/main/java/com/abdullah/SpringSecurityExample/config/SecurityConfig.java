package com.abdullah.SpringSecurityExample.config;

import com.abdullah.SpringSecurityExample.provider.JwtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    JwtAuthenticationProvider jwtAuthenticationProvider;

    @Bean
    public AuthenticationManager getAuthenticationManager(AuthenticationConfiguration authenticationConfiguration){
        try{
            return authenticationConfiguration.getAuthenticationManager();
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to configure Authentication Manager: "+e.getMessage());
        }
    }

    @Autowired void registerProvider(AuthenticationManagerBuilder authManBuilder){
        try{
           authManBuilder.authenticationProvider(jwtAuthenticationProvider);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to register Authentication Providers: "+e.getMessage());
        }
    }

    @Bean
    public PasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http){
        try{
            return http.build();
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to configure security filter chain: "+e.getMessage());
        }
    }
}
