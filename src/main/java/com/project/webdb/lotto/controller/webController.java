package com.project.webdb.lotto.controller;

import com.project.webdb.lotto.domain.LottoStoreEntity;
import com.project.webdb.lotto.domain.LottoStoreRepository;
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

    @GetMapping("/")
    public String main_page(Model m) {
        List<LottoStoreEntity> storeEntities = storeRepo.findAll();

        m.addAttribute("storeData", storeEntities);

        return "mainPage";
    }

    @GetMapping("/findStore")
    public String find_page() {
        return "findStorePage";
    }
}
