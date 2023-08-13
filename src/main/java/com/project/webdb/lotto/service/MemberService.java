package com.project.webdb.lotto.service;

import com.project.webdb.lotto.constant.Role;
import com.project.webdb.lotto.domain.MemberEntity;
import com.project.webdb.lotto.domain.MemberRepository;
import com.project.webdb.lotto.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class MemberService {
    //계정 생성
    @Autowired
    MemberRepository memberRepo;

    public void regist(MemberDto member) {
        if (memberRepo.findByNickname(member.getNickname()).isPresent() || memberRepo.findByEmail(member.getEmail()).isPresent()) {
            throw new RuntimeException();
        }
        if (!member.getPwd().equals(member.getRe_pwd())) {
            throw new RuntimeException();
        }

        //BCrypt 처리
        memberRepo.save(MemberEntity.builder()
                .nickname(member.getNickname())
                .email(member.getEmail())
                .pwd(member.getPwd())
                //.pwd(passwordEncoder.encode(member.getPwd()))
                .createdTime(LocalDateTime.now())
                .role(Role.ROLE_USER)
                .build());
    }
}
