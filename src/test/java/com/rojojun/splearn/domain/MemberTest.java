package com.rojojun.splearn.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {

    @Test
    void createMember() {
        var member = new Member("hojun@splearn.app", "Rojojun", "secret");

        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void constructorNullCheck() {
        assertThatThrownBy(() -> new Member(null, "Rojojun", "secret"))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void activate() {
        var member = new Member("hojun@splearn.app", "Rojojun", "secret");

        member.activate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void activateFail() {
        var member = new Member("hojun@splearn.app", "Rojojun", "secret");

        member.activate();

        assertThatThrownBy(member::activate).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate() {
        var member = new Member("hojun@splearn.app", "Rojojun", "secret");

        member.activate();
        member.deactivate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATE);
    }

    @Test
    void deactivateFail() {
        var member = new Member("hojun@splearn.app", "Rojojun", "secret");

        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deactivate();

        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);
    }
}