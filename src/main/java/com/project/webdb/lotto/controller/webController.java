package com.project.webdb.lotto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class webController {

    @GetMapping("/")
    public String main_page(Model model) {
        return "findStorePage";
    }
}
