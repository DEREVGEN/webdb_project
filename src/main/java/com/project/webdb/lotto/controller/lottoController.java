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
public class lottoController {

    @Autowired
    lottoRepository repo;

    @Autowired
    lottoFindingService service;

    @GetMapping("/")
    public String main_page() {
        return "mainPage";
    }

    // ajax request
    @PostMapping("/getLocation")
    public String location_page(@RequestBody String gps, Model m) throws ParseException {
        /*사용자의 위치(json)값을 파싱하여 데이터 획득
          사용자의 위치에 대한 정보를 토대로, findingService를 구현하였는데,
          이 객체는 위치정보를 받고, 인근 주변 5개의 로또 당첨집을 뿌려줌.
         */
        JSONParser jp = new JSONParser();
        JSONObject jo = (JSONObject) jp.parse(gps);

        double lat = Double.parseDouble(jo.get("lat").toString());
        double lon = Double.parseDouble(jo.get("lon").toString());

        List<lottoEntity> lottoEntityList = service.find(lat, lon);

        m.addAttribute("lottoData", lottoEntityList);

        return "mainPage :: #thymeTable";
    }

    //test code below
    @GetMapping("/btnTable")
    public String btn_table(Model m) {

        return "buttonPage";
    }

    @PostMapping("/btnTable")
    public String get_lotto_table(Model m) {
        List<lottoEntity> entityList = repo.findAll();

        m.addAttribute("lottoData", entityList);

        return "buttonPage :: #thymeTable";
    }
}
