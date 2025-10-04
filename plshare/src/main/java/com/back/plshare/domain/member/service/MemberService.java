package com.back.plshare.domain.member.service;

import com.back.plshare.domain.member.entity.Member;
import com.back.plshare.domain.member.repositoy.MemberRepository;
import com.back.plshare.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    @Transactional
    public Member join(String username, String nickname, String email, String rawpassword) {
        findByUsername(username).ifPresent((member) -> {
            throw new ServiceException("404-1", "이미 사용중인 이메일입니다.");
        });

        String encodedPassword = passwordEncoder.encode(rawpassword);

        Member member = new Member(username, nickname, email, encodedPassword);
        member.updateRefreshToken(authService.genRefreshToken(member));     // refresh토큰 설정

        return memberRepository.save(member);
    }

    @Transactional
    public Member login(String username, String rawpassword) {
        Member member = memberRepository.findByUsername(username).orElseThrow(
                () -> new ServiceException("401-1", "존재하지 않는 아이디입니다."));

        if(member.isDeleted()){
            throw new ServiceException("401", "탈퇴한 계정입니다.");
        }

        checkPassword(rawpassword, member.getPassword());

        member.updateRefreshToken(authService.genRefreshToken(member));
        return memberRepository.save(member);
    }

    @Transactional
    public void logout(Member member) {
        // RefreshToken 제거
        member.clearRefreshToken();
        memberRepository.save(member);
    }

    public void checkPassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new ServiceException("401", "비밀번호가 일치하지 않습니다");
        }
    }
}
