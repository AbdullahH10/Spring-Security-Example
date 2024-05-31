package com.abdullah.SpringSecurityExample.provider;

import com.abdullah.SpringSecurityExample.service.DbUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class JwtAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    DbUserDetailsService dbUserDetailsService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = dbUserDetailsService.loadUserByUsername(email);

        if(userDetails != null){
            if(passwordEncoder.matches(password,userDetails.getPassword())){
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        email,
                        password,
                        userDetails.getAuthorities()
                );

                return token;
            }
            throw new RuntimeException("Wrong password.");
        }
        throw new RuntimeException("User details not found");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
