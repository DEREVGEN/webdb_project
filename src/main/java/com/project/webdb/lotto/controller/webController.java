package com.project.webdb.lotto.controller;

import com.project.webdb.lotto.domain.lottoEntity;
import com.project.webdb.lotto.domain.lottoRepository;
import com.project.webdb.lotto.service.lottoFindingService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class webController {

    @GetMapping("/")
    public String main_page() {
        return "mainPage";
    }
}
