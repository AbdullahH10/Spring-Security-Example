package com.abdullah.SpringSecurityExample.repository;

import com.abdullah.SpringSecurityExample.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, BigInteger> {
    Optional<UserEntity> findByEmail(String email);
}
