package com.back.plshare.domain.member.service;

import com.back.plshare.domain.member.entity.Member;
import com.back.plshare.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtProvider jwtProvider;

    public String genAccessToken(Member member) {
        return jwtProvider.generateAccessToken(member);
    }

    public String genRefreshToken(Member member) {
        return jwtProvider.generateRefreshToken(member);
    }

    public boolean validateToken(String token) {
        return jwtProvider.isValid(token);
    }

    public Map<String, Object> parseToken(String token) {
        return jwtProvider.payloadOrNull(token);
    }
}
