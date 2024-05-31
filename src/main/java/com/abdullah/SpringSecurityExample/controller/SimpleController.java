package com.abdullah.SpringSecurityExample.controller;

import com.abdullah.SpringSecurityExample.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class SimpleController {
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
                    .body("Something went wrong!");
        }
    }
}
