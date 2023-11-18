package com.hanghae.spartagoods.entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRoleEnum {
    ADMIN("ADMIN"),  // ADMIN 권한
    USER("USER");

    private final String authority;

    public String getAuthority() {
        return this.authority;
    }
}