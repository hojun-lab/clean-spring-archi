package com.rojojun.splearn.application.provided;

import com.rojojun.splearn.domain.Member;
/**
*
*/
public interface MemberFinder {
    Member find(Long memberId);
}
