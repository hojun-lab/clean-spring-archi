package com.rojojun.splearn.application.member;

import com.rojojun.splearn.application.member.provided.MemberFinder;
import com.rojojun.splearn.application.member.required.MemberRepository;
import com.rojojun.splearn.domain.member.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Validated
@Transactional
@RequiredArgsConstructor
@Service
public class MemberQueryService implements MemberFinder {
    private final MemberRepository memberRepository;

    @Override
    public Member find(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. id : " +  memberId));
    }
}
