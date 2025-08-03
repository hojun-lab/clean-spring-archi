package com.rojojun.splearn.adpter.integration;

import com.rojojun.splearn.domain.shared.Email;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;

import static org.assertj.core.api.Assertions.assertThat;

class DummyEmailSenderTest {
    @Test
    @StdIo
    void dummyEmailSender(StdOut out) {
        DummyEmailSender dummyEmailSender = new DummyEmailSender();
        dummyEmailSender.send(new Email("me@gmail.com"), "rojojun", "hihi");

        assertThat(out.capturedLines()[0]).isEqualTo("DummyEmailSender send email: Email[address=me@gmail.com]");
    }
}