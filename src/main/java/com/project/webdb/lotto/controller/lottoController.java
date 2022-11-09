package com.project.webdb.lotto.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
    public String location_page(@RequestBody String gps) throws ParseException { //사용자의 위치(json)값을 파싱하여 데이터 획득
        JSONParser jp = new JSONParser();
        JSONObject jo = (JSONObject) jp.parse(gps);

        String lat = jo.get("lat").toString();
        String lon = jo.get("lon").toString();

        System.out.println("lat: " + lat + " lon: " + lon);
        return "good";
    }
}
