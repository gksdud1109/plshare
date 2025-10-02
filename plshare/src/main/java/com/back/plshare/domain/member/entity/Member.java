package com.back.plshare.domain.member.entity;

import com.back.plshare.global.jpa.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Member extends BaseEntity {

    @Column(unique=true, nullable = false)
    private String username;

    @Column(unique=true, nullable = false)
    private String nickname;

    @Column(unique=true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    @Column(nullable = false)
    private boolean deleted = false;

    @Setter
    private LocalDateTime deletedAt;
}
