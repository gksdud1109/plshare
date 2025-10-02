package com.back.plshare.domain.member.service;

import com.back.plshare.domain.member.entity.Member;
import com.back.plshare.domain.member.repositoy.MemberRepository;
import com.back.plshare.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    @Transactional
    public Member join(String username, String nickname, String email, String password) {
        findByUsername(username).ifPresent((member) -> {
            throw new ServiceException("404-1", "이미 사용중인 이메일입니다.");
        });

        Member member = new Member(username, nickname, email, password);

        return memberRepository.save(member);
    }
}
