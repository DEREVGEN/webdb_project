package com.project.webdb.lotto.controller;

import com.project.webdb.lotto.dto.MemberDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class signController {



    @PostMapping("/signUp")
    @ResponseBody
    public MemberDto sign_up_page(MemberDto member) {
        return member;
    }
}
