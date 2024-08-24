package com.abdullah.SpringSecurityExample.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDTO {
    String email;
    String password;
    String address;
    String phone;
    String status;
}
