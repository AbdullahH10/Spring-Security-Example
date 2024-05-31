package com.abdullah.SpringSecurityExample.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO<T>{
    HttpStatus statusCode;
    String message;
    T data;
}
