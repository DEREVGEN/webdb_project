package com.project.webdb.lotto.service;

import com.project.webdb.lotto.domain.lottoEntity;
import com.project.webdb.lotto.domain.lottoRepository;
import org.json.simple.parser.ParseException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class lottoServiceTest {

    @Autowired
    lottoParsingService lottoService;
    @Autowired
    lottoRepository lottoRepo;

    @Test
    void service_test() throws IOException, ParseException {
        //파싱테스트

        String URL = "https://dhlottery.co.kr/store.do?method=topStore&pageGubun=L645";
        Connection conn = Jsoup.connect(URL);

        Document document = conn.get();

        //회차 파싱

        Elements titleValue = document.select("table.tbl_search_opt tbody tr td select#drwNo option[selected]");

        for (Element element : titleValue) {
            System.out.println(element.text()+ " 회차에 관한 정보입니다.");
        }


        //본문 파싱
        Element parsingTable = document.select("table.tbl_data_col").get(0);

        Elements parsingTr = parsingTable.select("tbody tr");

        for (Element element : parsingTr) {
            String lotto_name = element.select("td").get(1).text();
            String auto = element.select("td").get(2).text();
            String lotto_address = element.select("td").get(3).text();
            Map<String, Double> geoData = lottoService.where(lotto_address);

            System.out.println("name: " + lotto_name + " auto?: " + auto +" address: " + lotto_address + " geo: " + geoData);
        }
    }

    @Test
    @DisplayName("서비스 기능 테스트")
    void service_test2() throws IOException, ParseException {
        List<lottoEntity> lottoEntites = lottoService.update();

        for (lottoEntity lottoEn : lottoEntites) {
            lottoRepo.save(lottoEn);
        }
    }

    @Test
    @DisplayName("레포지터리 테스트")
    void repo_test() {
        lottoRepo.save(lottoEntity.builder()
                .nickname("야후")
                .auto(true)
                .round(1400)
                .lat(25.5)
                .lon(26.0)
                .location("야후")
                .build());
    }
}