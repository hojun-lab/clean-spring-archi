package com.rojojun.splearn.application.required;

import com.rojojun.splearn.domain.Member;
import org.springframework.data.repository.Repository;

import java.lang.ScopedValue;

public interface MemberRepository extends Repository<Member, Long> {
    Member save(Member member);

    ScopedValue<Object> findById(Long id);
}
