package com.project.webdb.lotto.controller;

import com.project.webdb.lotto.dto.LocationDto;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class lottoController {

    @GetMapping("/")
    public String main_page() {
        return "mainPage";
    }

    @PostMapping("/getLocation")
    @ResponseBody
    public String location_page(LocationDto locationDto) throws ParseException {
        System.out.println(locationDto.toString());

        return "good";
    }
}
