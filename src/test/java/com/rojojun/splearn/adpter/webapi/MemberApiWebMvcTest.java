package com.rojojun.splearn.adpter.webapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rojojun.splearn.application.member.provided.MemberRegister;
import com.rojojun.splearn.domain.MemberFixture;
import com.rojojun.splearn.domain.member.Member;
import com.rojojun.splearn.domain.member.MemberRegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(MemberApi.class)
class MemberApiWebMvcTest {
    @MockitoBean
    private MemberRegister memberRegister;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    MockMvcTester mvcTester;

    @Test
    void register() throws JsonProcessingException {
        Member member = MemberFixture.createMember(1L);
        when(memberRegister.register(any())).thenReturn(member);
        MemberRegisterRequest request = MemberFixture.createMemberRegisterRequest();
        String requestJson = objectMapper.writeValueAsString(request);

        assertThat(mvcTester.post().uri("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$.memberId").asNumber().isEqualTo(1);

        verify(memberRegister).register(request);
    }

    @Test
    void registerFail() throws JsonProcessingException {
        MemberRegisterRequest request = MemberFixture.createMemberRegisterRequest("invalid email");
        String requestJson = objectMapper.writeValueAsString(request);

        assertThat(mvcTester.post().uri("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .hasStatus(HttpStatus.BAD_REQUEST);
    }
}