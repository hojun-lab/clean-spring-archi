package com.rojojun.splearn.application;

import com.rojojun.splearn.application.provided.MemberFinder;
import com.rojojun.splearn.application.provided.MemberRegister;
import com.rojojun.splearn.application.required.EmailSender;
import com.rojojun.splearn.application.required.MemberRepository;
import com.rojojun.splearn.domain.DuplicateEmailException;
import com.rojojun.splearn.domain.Member;
import com.rojojun.splearn.domain.MemberRegisterRequest;
import com.rojojun.splearn.domain.PasswordEncoder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Transactional
@RequiredArgsConstructor
@Service
public class MemberModifyService implements MemberRegister {
    private final MemberFinder memberFinder;
    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member register(MemberRegisterRequest registerRequest) {
        checkDuplicateEmail(registerRequest);

        Member member = Member.register(registerRequest, passwordEncoder);

        memberRepository.save(member);

        sendWelcomeEmail(member);

        return member;
    }

    @Override
    public Member activate(Long memberId) {
        Member member = memberFinder.find(memberId);

        member.activate();

        return memberRepository.save(member);
    }

    private void sendWelcomeEmail(Member member) {
        emailSender.send(member.getEmail(), "등록을 완료해주새요", "아래 링크를 클릭해서 등록을 완료해주세요");
    }

    private void checkDuplicateEmail(MemberRegisterRequest registerRequest) {
        if (memberRepository.findByEmail(registerRequest.email()).isPresent()) {
            throw new DuplicateEmailException("이미 사용 중인 이메일 입니다: " + registerRequest.email());
        }
    }
}
