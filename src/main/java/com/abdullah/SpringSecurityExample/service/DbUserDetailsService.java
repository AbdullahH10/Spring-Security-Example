package com.abdullah.SpringSecurityExample.service;

import com.abdullah.SpringSecurityExample.entity.UserEntity;
import com.abdullah.SpringSecurityExample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try{
            Optional<UserEntity> userEntityOptional = userRepository.findByEmail(email);
            if(userEntityOptional.isPresent()){
                UserEntity user = userEntityOptional.get();
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                UserDetails userDetails = User.builder()
                        .username(user.getEmail())
                        .password(user.getPassword())
                        .authorities(authorities)
                        .build();

                return userDetails;
            }
            throw new RuntimeException("User not present in database.");
        }
        catch (Exception e){
            throw new RuntimeException("User details not found.");
        }
    }
}
