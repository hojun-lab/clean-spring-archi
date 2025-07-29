package com.rojojun.splearn.application.provided;

import com.rojojun.splearn.application.MemberService;
import com.rojojun.splearn.application.required.EmailSender;
import com.rojojun.splearn.application.required.MemberRepository;
import com.rojojun.splearn.domain.Email;
import com.rojojun.splearn.domain.Member;
import com.rojojun.splearn.domain.MemberFixture;
import com.rojojun.splearn.domain.MemberStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.ScopedValue;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalMatchers.eq;
import static org.mockito.ArgumentMatchers.any;

class MemberRegisterManualTest {
    @Test
    void registerTestStub() {
        MemberRegister register = new MemberService(
                new MemberRepositoryStub(),
                new MailSenderStub(),
                MemberFixture.createPasswordEncoder()
        );

        Member member = register.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void registerTestMock() {
        MailSenderMock mailSender = new MailSenderMock();

        MemberRegister register = new MemberService(
                new MemberRepositoryStub(),
                mailSender,
                MemberFixture.createPasswordEncoder()
        );

        Member member = register.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);

        Mockito.verify(mailSender).send(eq(member.getEmail(), any(), any()));
    }

    @Test
    void registerTestMockito() {
        EmailSender mailSender = Mockito.mock(EmailSender.class);

        MemberRegister register = new MemberService(
                new MemberRepositoryStub(),
                mailSender,
                MemberFixture.createPasswordEncoder()
        );

        Member member = register.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);

        assertThat(mailSender.emails).hasSize(1);
        assertThat(mailSender.emails.getFirst()).isEqualTo(member.getEmail());
    }


    static class MemberRepositoryStub implements MemberRepository {

        @Override
        public Member save(Member member) {
            ReflectionTestUtils.setField(member, "id", 1);
            return member;
        }
//
//        @Override
//        public ScopedValue<Object> findById(Long id) {
//            return null;
//        }
    }

    static class MailSenderStub implements EmailSender {
        @Override
        public void send(Email email, String subject, String body) {

        }
    }

    static class MailSenderMock implements EmailSender {
        List<Email> emails = new ArrayList<>();

        @Override
        public void send(Email email, String subject, String body) {

        }
    }
}