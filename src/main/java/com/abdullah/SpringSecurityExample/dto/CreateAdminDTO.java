package com.abdullah.SpringSecurityExample.dto;

import com.abdullah.SpringSecurityExample.authority.Role;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdminDTO {
    String email;
    String password;
    String address;
    String phone;
    String status;
}
