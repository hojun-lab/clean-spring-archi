package com.rojojun.splearn.domain.member;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {
    @Test
    void profile() {
        new Profile("rojojun");
        new Profile("123456");
        new Profile("rojojun12");
    }

    @Test
    void profileFail() {
        new Profile("");
        new Profile("A");
        new Profile("프로필");
    }

    @Test
    void url() {
        var profile = new Profile("rojojun");

        assertThat(profile.url()).isEqualTo("@rojojun");
    }
}