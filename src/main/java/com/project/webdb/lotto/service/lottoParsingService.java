package com.project.webdb.lotto.service;



import com.project.webdb.lotto.domain.lottoEntity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.*;

@Service
public class lottoParsingService {

    /* lotto엔티티 추가
        1. 로또 사이트 파싱
        2. 로또 사이트 파싱된 데이터를 naver geocode api
        3. 해당 api를 파싱하여, 위도 경도값 알아냄
     */

    @Value("${naver.geocode.api.id}")
    String api_key;
    @Value("${naver.geocode.api.secret}")
    String secret_key;

    public List<lottoEntity> update() throws IOException, ParseException { //로또 사이트 파싱
        String URL = "https://dhlottery.co.kr/store.do?method=topStore&pageGubun=L645";
        Connection conn = Jsoup.connect(URL);

        Document document = conn.get();

        int round;

        //회차 파싱
        Elements titleValue = document.select("table.tbl_search_opt tbody tr td select#drwNo option[selected]");
        round = Integer.parseInt(titleValue.get(0).text());

        //본문 파싱
        Element parsingTable = document.select("table.tbl_data_col").get(0);
        Elements parsingTr = parsingTable.select("tbody tr");

        List<lottoEntity> lottoEntities = new ArrayList<>();

        //파싱된 데이터를 entity에 추가
        for (Element element : parsingTr) {
            String lotto_name = element.select("td").get(1).text();
            String auto = element.select("td").get(2).text();
            String lotto_address = element.select("td").get(3).text();
            Map<String, Double> geoData = this.where(lotto_address);

            lottoEntities.add(lottoEntity.builder()
                    .nickname(lotto_name)
                    .round(round)
                    .location(lotto_address)
                    .lon(geoData.get("lon"))
                    .lat(geoData.get("lat"))
                    .auto(auto.equals("자동") ? true : false)
                    .build());

            System.out.println("name: " + lotto_name + " auto?: " + auto +" address: " + lotto_address + " geo: " + geoData);
        }

        return lottoEntities;
    }

    Map<String, Double> where(String location) throws ParseException { // naver geocode api 사용하는 곳

        // 위치 정보 값 따오기
        String url = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode";
        String apiUrl = UriComponentsBuilder.newInstance()
                .fromHttpUrl(url)
                .queryParam("query", "{query}")
                .encode()
                .toUriString();

        Map<String, String> apiParams = new LinkedHashMap<>();
        apiParams.put("query", location);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-NCP-APIGW-API-KEY-ID", api_key);
        httpHeaders.add("X-NCP-APIGW-API-KEY", secret_key);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        RestTemplate rt = new RestTemplate();

        ResponseEntity<String> geoRes = rt.exchange(apiUrl, HttpMethod.GET, httpEntity, String.class, apiParams);

        return parsing_location(geoRes.getBody());
    }

    Map<String, Double> parsing_location(String locationData) throws ParseException { // 위치 api의 json 값에 대한 위도, 경도 파싱
        JSONParser jp = new JSONParser();
        JSONObject jo = (JSONObject) jp.parse(locationData);

        JSONArray ja = (JSONArray) jo.get("addresses");

        JSONObject gps = (JSONObject)ja.get(0);

        Map<String, Double> gpsData = new HashMap<>();

        String x = gps.get("x").toString();
        String y = gps.get("y").toString();

        gpsData.put("lon", Double.parseDouble(x));
        gpsData.put("lat", Double.parseDouble(y));

        return gpsData;
    }
}
