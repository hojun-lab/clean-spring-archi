package com.rojojun.splearn.adpter.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecurePasswordEncoderTest {
    @Test
    void securePasswordEncoder() {
        SecurePasswordEncoder securePasswordEncoder = new SecurePasswordEncoder();

        String passwordHash = securePasswordEncoder.encode("secret");

        assertTrue(securePasswordEncoder.matches("secret", passwordHash));
        assertFalse(securePasswordEncoder.matches("secret22", passwordHash));
    }
}