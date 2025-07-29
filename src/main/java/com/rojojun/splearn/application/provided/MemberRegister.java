package com.rojojun.splearn.application.provided;

import com.rojojun.splearn.domain.Member;
import com.rojojun.splearn.domain.MemberRegisterRequest;

/**
 * 회원의 등록과 관련된 기능을 제공한다
 */
public interface MemberRegister {
    Member register(MemberRegisterRequest registerRequest);
}
