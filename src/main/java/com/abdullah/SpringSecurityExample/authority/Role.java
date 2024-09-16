package com.abdullah.SpringSecurityExample.authority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.abdullah.SpringSecurityExample.authority.Permission.READ;
import static com.abdullah.SpringSecurityExample.authority.Permission.WRITE;
import static com.abdullah.SpringSecurityExample.authority.Permission.UPDATE;
import static com.abdullah.SpringSecurityExample.authority.Permission.DELETE;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN(Set.of(READ,WRITE,UPDATE,DELETE)),
    USER(Set.of(READ,WRITE,UPDATE));

    private final Set<Permission> role;

    public List<? extends GrantedAuthority> getAuthorities(){
        List<SimpleGrantedAuthority> authorities = getRole().stream().map(
                (permission) -> new SimpleGrantedAuthority(permission.getPermission())
        ).collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));

        return authorities;
    }
}
