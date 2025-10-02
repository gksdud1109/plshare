package com.back.plshare.domain.member.controller;

import com.back.plshare.domain.member.dto.MemberDto;
import com.back.plshare.domain.member.dto.MemberJoinReqBody;
import com.back.plshare.domain.member.entity.Member;
import com.back.plshare.domain.member.service.MemberService;
import com.back.plshare.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
@Tag(name = "ApiV1MemberController", description = "고객정보 API")
public class MemberController {

    private final MemberService memberService;


    record joinResBody(
       MemberDto memberDto
    ){}
    @PostMapping("/join")
    @Operation(summary = "회원가입")
    public RsData<MemberDto> join(
            @RequestBody @Valid MemberJoinReqBody reqBody
    ) {
        Member member = memberService
                .join(reqBody.username(), reqBody.nickname(), reqBody.email(), reqBody.password());

        return new RsData(
                "201-1",
                "고객정보가 저장되었습니다.",
                new joinResBody(
                        new MemberDto(member)
                )
        );
    }
}
