package com.abdullah.SpringSecurityExample.entity;

import com.abdullah.SpringSecurityExample.authority.Role;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity()
@Table(name = "USER_TBL")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    BigInteger id;
    String email;
    String password;
    String address;
    String phone;
    String status;
    Role role;
}
