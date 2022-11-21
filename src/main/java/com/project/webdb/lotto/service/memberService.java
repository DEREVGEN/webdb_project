package com.project.webdb.lotto.service;

import com.project.webdb.lotto.domain.MemberEntity;
import com.project.webdb.lotto.domain.MemberRepository;
import com.project.webdb.lotto.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class memberService {
    //계정 생성
    @Autowired
    MemberRepository memberRepo;

    public void save(MemberDto member) {
        if (memberRepo.findByNickname(member.getNickname()).isPresent() || memberRepo.findByEmail(member.getEmail()).isPresent()) {
            //오류처리
        }
        memberRepo.save(MemberEntity.builder()
                .nickname(member.getNickname())
                .email(member.getEmail())
                .pwd(member.getPwd())
                .createdTime(LocalDateTime.now())
                .build());
    }
}
