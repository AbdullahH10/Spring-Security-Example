package com.abdullah.SpringSecurityExample.config;

import com.abdullah.SpringSecurityExample.filter.JwtFilter;
import com.abdullah.SpringSecurityExample.provider.JwtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    JwtAuthenticationProvider jwtAuthenticationProvider;
    @Autowired
    JwtFilter jwtFilter;

    @Bean
    public AuthenticationManager getAuthenticationManager(AuthenticationConfiguration authenticationConfiguration){
        try{
            return authenticationConfiguration.getAuthenticationManager();
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to configure Authentication Manager: "+e.getMessage());
        }
    }

    @Autowired
    public void registerProvider(AuthenticationManagerBuilder authManBuilder){
        try{
           authManBuilder.authenticationProvider(jwtAuthenticationProvider);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to register Authentication Providers: "+e.getMessage());
        }
    }

    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http){
        try{
            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(requests -> requests
                            .requestMatchers("/signup").permitAll()
                            .requestMatchers("/login").permitAll()
                            .anyRequest().authenticated());
            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
            return http.build();
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to configure security filter chain: "+e.getMessage());
        }
    }
}
