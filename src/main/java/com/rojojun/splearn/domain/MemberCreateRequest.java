package com.rojojun.splearn.domain;

public record MemberCreateRequest(
        String email,
        String nickname,
        String password
) {
}
