package com.project.webdb.lotto.controller;

import com.project.webdb.lotto.domain.LottoDataRepository;
import com.project.webdb.lotto.domain.LottoStoreEntity;
import com.project.webdb.lotto.domain.LottoStoreRepository;
import com.project.webdb.lotto.dto.MemberDto;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class webController {

    @Autowired
    LottoStoreRepository storeRepo;

    @Autowired
    LottoDataRepository dataRepo;


    @GetMapping("/")
    public String main_page(Model m) {
        //m.addAttribute("storeData", storeRepo.findAll());
        m.addAttribute("roundData", dataRepo.findAll());

        return "mainPage";
    }

    @GetMapping("/findStore")
    public String find_page() {
        return "findStorePage";
    }

    @GetMapping("/signIn")
    public String sign_in_page() {
        return "sign/signInPage";
    }

    @GetMapping("/signUp")
    public String sign_up_page(Model model) {
        model.addAttribute("memberFormDto", new MemberDto());

        return "sign/signUpPage";
    }
}
