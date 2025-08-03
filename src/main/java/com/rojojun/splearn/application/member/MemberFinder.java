package com.rojojun.splearn.application.member.provided;

import com.rojojun.splearn.domain.member.Member;
/**
*
*/
public interface MemberFinder {
    Member find(Long memberId);
}
