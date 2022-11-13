package com.project.webdb.lotto.service;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class lottoDataEntityParsingServiceTest {

    @Autowired
    lottoParsingService lottoService;

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
    @DisplayName("로또 당첨번호 파싱 테스트")
    void lotto_win_number_parsing_test() throws IOException {
        String URL = "https://dhlottery.co.kr/gameResult.do?method=byWin";
        Connection conn = Jsoup.connect(URL);

        Document document = conn.get();

        //회차파싱
        Element roundValue = document.select(".win_result h4 strong").get(0);
        int round = Integer.parseInt(roundValue.text().substring(0, roundValue.text().lastIndexOf("회")));

        //당첨번호 파싱
        List<Integer> winNumList = new ArrayList<>();
        Elements winNumbers = document.select(".win p span");
        for (Element number : winNumbers) {
            winNumList.add(Integer.parseInt(number.text()));
        }
        Element bonusNum = document.select(".bonus p").get(0);
        int bonus = Integer.parseInt(bonusNum.text());

        System.out.println(round + "회차 입니다.");
        System.out.println("당첨번호: ");
        for (int num : winNumList) {
            System.out.print(num + ", ");
        }
        System.out.println("보너스 번호: " + bonus);
    }
}