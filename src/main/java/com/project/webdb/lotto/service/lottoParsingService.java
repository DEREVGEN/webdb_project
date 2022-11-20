package com.project.webdb.lotto.service;

import com.project.webdb.lotto.domain.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@Service
public class lottoParsingService {

    /* 로또 파싱 서비스
        1.
          1.1 로또 사이트 파싱 - LottoDataEntity 구성
          1.2 로또 당첨지역 파싱 - LottoStoreEntity 구성
        2. 로또 사이트 파싱된 데이터를 naver geocode api - LottoGeoEntity 구성
     */

    @Value("${naver.geocode.api.id}")
    String api_key;
    @Value("${naver.geocode.api.secret}")
    String secret_key;

    @Autowired
    LottoDataRepository lottoDataRepo;
    @Autowired
    LottoStoreRepository lottoStoreRepo;

    @Autowired
    LottoGeoRepository lottoGeoRepo;

    @Transactional
    public void update() throws IOException, ParseException { //로또 사이트 파싱


        //1.1 로또 사이트 파싱 - LottoDataEntity 구성
        LottoDataEntity lottoData = parsing_lottoWinNum();

        //1.2 로또 당첨지역 파싱 - LottoStoreEntity 구성
        List<LottoStoreEntity> lottoStores = parsing_lottoStore(lottoData);

        //2. 로또 사이트 파싱된 데이터를 naver geocode api - LottoGeoEntity 구성
        parsing_lottoGeo(lottoStores);

    }

    LottoDataEntity parsing_lottoWinNum() throws IOException {
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


        LottoDataEntity lottoData = LottoDataEntity.builder()
                .num1(winNumList.get(0))
                .num2(winNumList.get(1))
                .num3(winNumList.get(2))
                .num4(winNumList.get(3))
                .num5(winNumList.get(4))
                .num6(winNumList.get(5))
                .bonus(bonus)
                .id(round)
                .build();

        lottoDataRepo.save(lottoData);

        return lottoData;
    }

    List<LottoStoreEntity> parsing_lottoStore(LottoDataEntity lottoData) throws IOException {
        String URL = "https://dhlottery.co.kr/store.do?method=topStore&pageGubun=L645";
        Connection conn = Jsoup.connect(URL);

        Document document = conn.get();

        Element parsingTable = document.select("table.tbl_data_col").get(0);
        Elements parsingTr = parsingTable.select("tbody tr");

        List<LottoStoreEntity> storeEntities = new ArrayList<>();

        //파싱된 데이터를 entity에 추가
        for (Element element : parsingTr) {
            String lotto_name = element.select("td").get(1).text();
            String auto = element.select("td").get(2).text();
            String lotto_address = element.select("td").get(3).text();



            storeEntities.add(LottoStoreEntity.builder()
                    .nickname(lotto_name)
                    .location(lotto_address)
                    .auto(auto.equals("자동"))
                    .lottoDataEntity(lottoData)
                    .build());
        }

        lottoStoreRepo.saveAll(storeEntities);

        return storeEntities;
    }

    void parsing_lottoGeo(List<LottoStoreEntity> lottoStores) throws ParseException {
        for (LottoStoreEntity lottoStore : lottoStores) {
            Map<String, Double> geoData = where(lottoStore.getLocation());
            lottoGeoRepo.save(LottoGeoEntity.builder()
                    .storeEntity(lottoStore)
                    .lon(geoData.get("lon"))
                    .lat(geoData.get("lat"))
                    .build());
        }
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
        Map<String, Double> gpsData = new HashMap<>();

        JSONParser jp = new JSONParser();
        JSONObject jo = (JSONObject) jp.parse(locationData);

        try {
            JSONArray ja = (JSONArray) jo.get("addresses");
            JSONObject gps = (JSONObject) ja.get(0);

            String x = gps.get("x").toString();
            String y = gps.get("y").toString();

            gpsData.put("lon", Double.parseDouble(x));
            gpsData.put("lat", Double.parseDouble(y));
        } catch (Exception e) {
            //위치 정보가 파싱되지 않는 경우에..
            gpsData.put("lon", 0.0);
            gpsData.put("lat", 0.0);
        }

        return gpsData;
    }
}
