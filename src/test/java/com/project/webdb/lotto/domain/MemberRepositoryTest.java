package com.project.webdb.lotto.domain;

import com.project.webdb.lotto.constant.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepo;

    @Test
    @DisplayName("member 레포지터리 테스트")
    public void repo_test() {
         memberRepo.save(MemberEntity.builder()
                 .email("ydg983@naver.com")
                 .pwd("1234")
                 .nickname("ydg")
                 .createdTime(LocalDateTime.now())
                 .build());
    }

    @Test
    @DisplayName("member 레포지터리 기능 테스트")
    public void repo_test2() throws Exception {
        memberRepo.findByEmail("example2@naver.com").orElseThrow(() -> new Exception("not good"));
    }

    @Test
    @DisplayName("스프링부트 시큐리티 로그인 테스트")
    public void repo_test3() {
        memberRepo.save(MemberEntity.builder()
                .email("example3@naver.com")
                .nickname("ydg")
                .role(Role.ROLE_USER)
                .createdTime(LocalDateTime.now())
                .build());
    }


}