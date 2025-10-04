package com.back.plshare.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MemberJoinReqBody(

        @NotBlank
        @Size(min = 4,max = 20)
        String username,

        @NotBlank
        @Size(min = 2,max = 15)
        String nickname,

        @NotBlank
        @Pattern(
                regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
                message = "올바른 이메일 형식이 아닙니다."
        )
        String email,

        @NotBlank
        @Size(min = 4, max = 30)
        String password
) {
}
