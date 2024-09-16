package com.abdullah.SpringSecurityExample.controller;

import com.abdullah.SpringSecurityExample.authority.Role;
import com.abdullah.SpringSecurityExample.dto.CreateAdminDTO;
import com.abdullah.SpringSecurityExample.dto.ResponseDTO;
import com.abdullah.SpringSecurityExample.entity.UserEntity;
import com.abdullah.SpringSecurityExample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping()
    public ResponseEntity<?> createAdmin(@RequestBody CreateAdminDTO admin){
        try{
            UserEntity adminEntity = UserEntity.builder()
                    .email(admin.getEmail())
                    .password(passwordEncoder.encode(admin.getPassword()))
                    .address(admin.getAddress())
                    .phone(admin.getPhone())
                    .status(admin.getStatus())
                    .role(Role.ADMIN)
                    .build();

            adminEntity = userService.saveUser(adminEntity);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(ResponseDTO.builder()
                            .statusCode(HttpStatus.OK)
                            .message("Admin created.")
                            .data("User id is: "+adminEntity.getId())
                            .build());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDTO.builder()
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                            .message("Could not create admin. "+e.getMessage())
                            .data(null)
                            .build());
        }
    }

    @GetMapping("/message")
    public ResponseEntity<?> getAdminMessage(){
        try{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ResponseDTO.builder()
                            .statusCode(HttpStatus.OK)
                            .message("Request successful.")
                            .data("Hello Admin!")
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
