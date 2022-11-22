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
        if (memberRepo.findByNickname(member.getNickname()).isPresent() || memberRepo.findByEmail(member.getEmail()).isPresent()) { // 닉네임이나 이메일이 이미 존재한다면
            throw new RuntimeException();
            //오류처리
        }
        if (!member.getPwd().equals(member.getRe_pwd())) { // 회원가입시 비밀번호와 재확인 비밀번호가 같이 않다면
            throw new RuntimeException();
        }

        memberRepo.save(MemberEntity.builder()
                .nickname(member.getNickname())
                .email(member.getEmail())
                //.pwd(passwordEncoder.encode(member.getPwd()))
                .pwd(member.getPwd())
                .createdTime(LocalDateTime.now())
                .role(Role.USER)
                .build());
    }
}
