package com.abdullah.SpringSecurityExample.entity;

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
}
