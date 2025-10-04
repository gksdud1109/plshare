package com.back.plshare.domain.member.controller;

import com.back.plshare.domain.member.dto.MemberCommonResBody;
import com.back.plshare.domain.member.dto.MemberDto;
import com.back.plshare.domain.member.dto.MemberJoinReqBody;
import com.back.plshare.domain.member.entity.Member;
import com.back.plshare.domain.member.service.AuthService;
import com.back.plshare.domain.member.service.MemberService;
import com.back.plshare.global.exception.ServiceException;
import com.back.plshare.global.jwt.JwtRq;
import com.back.plshare.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
@Tag(name = "ApiV1MemberController", description = "고객정보 API")
public class MemberController {

    private final MemberService memberService;
    private final AuthService authService;
    private final JwtRq jwtRq;


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
                new MemberCommonResBody(
                        new MemberDto(member)
                )
        );
    }


    record LoginReqBody(
            @NotBlank
            @Size(min = 4,max = 30)
            String username,

            @NotBlank
            @Size(min = 2, max = 30)
            String password
    ){}
    @PostMapping("/login")
    @Operation(summary = "로그인")
    public RsData<MemberDto> login(
            @RequestBody @Valid LoginReqBody reqBody
    ){
        Member member = memberService.login(reqBody.username(), reqBody.password());

        String accessToken =  authService.genAccessToken(member);
        String refreshToken = member.getRefreshToken();

        jwtRq.setCookie("accessToken", accessToken);
        jwtRq.setCookie("refreshToken", refreshToken);

        return new RsData(
                "200",
                "로그인 성공",
                new MemberCommonResBody(
                    new MemberDto(member)
                )
        );
    }

    @DeleteMapping("/logout")
    @Operation(summary = "로그아웃")
    public RsData<Void> logout() {
        Member actor = memberService.findByUsername(jwtRq.getActor().getUsername())
                .orElseThrow(() -> new ServiceException("401", "존재하지 않는 계정"));

        memberService.logout(actor);

        jwtRq.deleteCookie("accessToken");
        jwtRq.deleteCookie("refreshToken");

        return new RsData<>(
                "200",
                "로그아웃 되었습니다."
        );
    }

    @GetMapping("/me")
    @Transactional(readOnly = true)
    @Operation(summary = "내 정보 조회")
    public RsData<MemberDto> getUserByEmail() {
        Member member = memberService.findByUsername(jwtRq.getActor().getUsername()).get();

        return new RsData(
                "200",
                "내 정보 조회 성공",
                new MemberCommonResBody(
                        new MemberDto(member)
                )
        );
    }
}
