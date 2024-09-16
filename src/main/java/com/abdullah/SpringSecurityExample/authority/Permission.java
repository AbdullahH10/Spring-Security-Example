package com.abdullah.SpringSecurityExample.authority;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Permission {
    READ("read"),
    WRITE("write"),
    UPDATE("update"),
    DELETE("delete");

    private final String permission;
}
