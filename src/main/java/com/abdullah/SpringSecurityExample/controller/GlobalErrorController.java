package com.abdullah.SpringSecurityExample.controller;

import com.abdullah.SpringSecurityExample.dto.ResponseDTO;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalErrorController implements ErrorController {
    @RequestMapping("/error")
    ResponseEntity<?> error(){
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body(ResponseDTO.builder()
                        .statusCode(HttpStatus.NOT_IMPLEMENTED)
                        .message("Requested resource not implemented yet.")
                        .data(null)
                        .build());
    }
}
