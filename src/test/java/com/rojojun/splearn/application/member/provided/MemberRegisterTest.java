package com.rojojun.splearn.application.member.provided;

import com.rojojun.splearn.SplearnTestConfiguration;
import com.rojojun.splearn.domain.*;
import com.rojojun.splearn.domain.member.*;
import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import jakarta.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
record MemberRegisterTest(MemberRegister memberRegister, EntityManager entityManager) {

    @Test
    void register() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void duplicatedEmailFail() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThatThrownBy(() -> memberRegister.register(MemberFixture.createMemberRegisterRequest()))
                .isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    void activate() {
        Member member = registerMember();

        member = memberRegister.activate(member.getId());

        entityManager.flush();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(member.getDetail().getActivatedAt()).isNotNull();
    }

    @Test
    void deactivate() {
        Member member = registerMember();

        memberRegister.activate(member.getId());
        entityManager.flush();
        entityManager.clear();

        member = memberRegister.deactivate(member.getId());

        entityManager.flush();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
        assertThat(member.getDetail().getDeactivatedAt()).isNotNull();
    }

    @Test
    void updateInfo() {
        Member member = registerMember();

        memberRegister.activate(member.getId());
        entityManager.flush();
        entityManager.clear();

        member = memberRegister.updateInfo(member.getId(), new MemberInfoUpdateRequest("Meowe", "Rojo", "나"));

        assertThat(member.getDetail().getProfile().address()).isEqualTo("Rojo");
    }

    @Test
    void updateInfoFail() {
        Member member = registerMember();
        memberRegister.activate(member.getId());
        member = memberRegister.updateInfo(member.getId(), new MemberInfoUpdateRequest("Meowe", "Rojo", "나"));

        Member member2 = registerMember("rojo100@splearn.app");
        member2.activate();
        entityManager.flush();
        entityManager.clear();

        assertThatThrownBy(() ->
                memberRegister.updateInfo(member2.getId(), new MemberInfoUpdateRequest("Jamey", "Rojo", "난나?"))
        ).isInstanceOf(DuplicateProfileException.class);

        memberRegister.updateInfo(member2.getId(), new MemberInfoUpdateRequest("Jamey", "Rojo3", "난나?"));
        memberRegister.updateInfo(member.getId(), new MemberInfoUpdateRequest("Jamey", "", "난나?"));
    }

    private Member registerMember() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();
        return member;
    }

    private Member registerMember(String email) {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();
        return member;
    }

    @Test
    void memberNotFoundFail() {
        invalidRequest(new MemberRegisterRequest("zoloman316@gmail.com", "roro", "password"));
        invalidRequest(new MemberRegisterRequest("zoloman316@gmail.com", "roro00o0o0o0o0o0o0o0o0o0o0o0o0o0o0o0o0", "password"));
        invalidRequest(new MemberRegisterRequest("zoloman316.com", "rorojun", "password"));
        invalidRequest(new MemberRegisterRequest("zoloman@316.com", "rorojun", "pas"));
    }

    private void invalidRequest(MemberRegisterRequest invalid) {
        assertThatThrownBy(() -> memberRegister.register(invalid))
                .isInstanceOf(ConstraintViolationException.class);
    }
}


