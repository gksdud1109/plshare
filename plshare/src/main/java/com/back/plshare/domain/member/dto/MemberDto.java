package com.back.plshare.domain.member.dto;

import com.back.plshare.domain.member.entity.Member;

public record MemberDto(
        String username,
        String nickname,
        String email
) {
    public MemberDto(Member member){
        this(
                member.getUsername(),
                member.getNickname(),
                member.getEmail()
        );
    }
}
