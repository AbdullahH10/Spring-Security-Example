package com.abdullah.SpringSecurityExample.service;

import com.abdullah.SpringSecurityExample.entity.UserEntity;
import com.abdullah.SpringSecurityExample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try{
            UserEntity user = userRepository.findByEmail(email).orElse(null);
            if(user != null){
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                UserDetails userDetails = User.builder()
                        .username(user.getEmail())
                        .password(user.getPassword())
                        .authorities(authorities)
                        .build();

                return userDetails;
            }
            throw new RuntimeException("User not found.");
        }
        catch (Exception e){
            throw new RuntimeException("Error fetching user details: "+e.getMessage());
        }
    }

    public UserEntity saveUser(UserEntity userData){
        return userRepository.save(userData);
    }
}
