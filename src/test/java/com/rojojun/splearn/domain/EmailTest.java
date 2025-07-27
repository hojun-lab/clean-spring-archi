package com.rojojun.splearn.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmailTest {

    @Test
    void equality() {
        var email1 = new Email("rojojun@email.com");
        var email2 = new Email("rojojun@email.com");

        assertThat(email1).isEqualTo(email2);
    }
}