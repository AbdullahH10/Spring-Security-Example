package com.abdullah.SpringSecurityExample.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "USER_TBL")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    BigInteger id;
    String email;
    String password;
}
