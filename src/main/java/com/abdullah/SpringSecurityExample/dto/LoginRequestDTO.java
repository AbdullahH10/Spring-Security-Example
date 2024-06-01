package com.abdullah.SpringSecurityExample.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {
    String email;
    String password;
}
