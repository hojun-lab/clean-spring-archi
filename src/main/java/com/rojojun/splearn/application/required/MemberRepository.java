package com.rojojun.splearn.application.required;

import com.rojojun.splearn.domain.Email;
import com.rojojun.splearn.domain.Member;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface MemberRepository extends Repository<Member, Long> {
    Member save(Member member);

    Optional<Member> findByEmail(Email email);

    Optional<Member> findById(Long memberId);
}
