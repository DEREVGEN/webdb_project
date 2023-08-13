package com.project.webdb.lotto.domain;


import com.project.webdb.lotto.constant.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@ToString
@Table(name = "member")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String email;

    @Column
    String nickname;

    @Column
    String pwd;

    @Column
    LocalDateTime createdTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    Role role;

    @Builder
    public MemberEntity(String email, String nickname, String pwd, LocalDateTime createdTime, Role role) {
        this.email = email;
        this.nickname = nickname;
        this.pwd = pwd;
        this.createdTime = createdTime;
        this.role = role;
    }
}
