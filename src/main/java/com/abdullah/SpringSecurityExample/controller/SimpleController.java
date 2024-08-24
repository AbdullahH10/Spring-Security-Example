package com.abdullah.SpringSecurityExample.controller;

import com.abdullah.SpringSecurityExample.dto.LoginRequestDTO;
import com.abdullah.SpringSecurityExample.dto.ResponseDTO;
import com.abdullah.SpringSecurityExample.dto.SignUpRequestDTO;
import com.abdullah.SpringSecurityExample.entity.UserEntity;
import com.abdullah.SpringSecurityExample.service.UserService;
import com.abdullah.SpringSecurityExample.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class SimpleController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequestDTO userData){
        try{
            UserEntity user = UserEntity.builder()
                    .email(userData.getEmail())
                    .password(passwordEncoder.encode(userData.getPassword()))
                    .address(userData.getAddress())
                    .phone(userData.getPhone())
                    .status(userData.getStatus())
                    .build();

            user = userService.saveUser(user);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(ResponseDTO.builder()
                            .statusCode(HttpStatus.OK)
                            .message("User sign up successful.")
                            .data("User id is: "+user.getId())
                            .build());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDTO.builder()
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                            .message("Could not sign up user. "+e.getMessage())
                            .data(null)
                            .build());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginData){
        try{
            Authentication authToken = new UsernamePasswordAuthenticationToken(
                    loginData.getEmail(),
                    loginData.getPassword()
            );
            authToken = authenticationManager.authenticate(authToken);
            String token = jwtUtil.getToken(loginData.getEmail(),authToken.getAuthorities().toString());

            return ResponseEntity.status(HttpStatus.OK)
                    .body(ResponseDTO.builder()
                            .statusCode(HttpStatus.OK)
                            .message("Login successful")
                            .data(token)
                            .build());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDTO.builder()
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                            .message("Login attempt failed: "+e.getMessage())
                            .data(null)
                            .build());
        }
    }

    @GetMapping("/message")
    public ResponseEntity<?> getMessage(){
        try{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ResponseDTO.builder()
                            .statusCode(HttpStatus.OK)
                            .message("Request successful.")
                            .data("Hello World!")
                            .build());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDTO.builder()
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                            .message("Something went wrong! "+e.getMessage())
                            .data(null)
                            .build());
        }
    }
}
