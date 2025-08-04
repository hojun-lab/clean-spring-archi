package com.rojojun.splearn.adpter.webapi;

import com.rojojun.splearn.adpter.webapi.dto.MemberRegisterResponse;
import com.rojojun.splearn.application.member.provided.MemberRegister;
import com.rojojun.splearn.domain.member.Member;
import com.rojojun.splearn.domain.member.MemberRegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApi {
    private final MemberRegister memberRegister;

    @PostMapping("/api/members")
    public MemberRegisterResponse register(@RequestBody @Valid MemberRegisterRequest request) {
        Member member = memberRegister.register(request);

        return MemberRegisterResponse.of(member);
    }
}
