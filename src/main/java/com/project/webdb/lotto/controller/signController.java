package com.project.webdb.lotto.controller;

import com.project.webdb.lotto.dto.MemberDto;
import com.project.webdb.lotto.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
public class signController {

    @Autowired
    MemberService memberService;

    @PostMapping("/signUp")
    public String sign_up_page(@Valid MemberDto member, BindingResult bindingResult, Model model) {

        model.addAttribute("memberFormDto", member);

        if (bindingResult.hasErrors()) {
            return "/sign/signUpPage";
        }

        try {
            memberService.regist(member);
        } catch(Exception e) {
            return "/sign/signUpPage";
        }

        return "mainPage";
    }

}
