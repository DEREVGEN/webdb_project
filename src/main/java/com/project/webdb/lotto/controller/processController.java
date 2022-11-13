package com.project.webdb.lotto.controller;

import com.project.webdb.lotto.domain.LottoStoreEntity;
import com.project.webdb.lotto.service.lottoFindingService;
import com.project.webdb.lotto.service.lottoParsingService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
public class processController {

    @Autowired
    lottoParsingService parsingService;
    @Autowired
    lottoFindingService findingService;

    //data parsing - only [admin]
    @GetMapping("/parsingData")
    @ResponseBody
    public String parsing_data() throws IOException, ParseException {
        parsingService.update();
        return "done";
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

        List<LottoStoreEntity> nearStores = findingService.find(lat, lon);
        m.addAttribute("lottoData", nearStores);

        return "findStorePage :: #thymeTable";
    }
}
