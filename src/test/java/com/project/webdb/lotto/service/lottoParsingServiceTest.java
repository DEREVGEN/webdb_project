package com.project.webdb.lotto.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class lottoParsingServiceTest {

    @Value("${naver.geocode.api.id}")
    String api_key;
    @Value("${naver.geocode.api.secret}")
    String secret_key;

    @Test
    void naver_api_test() {
        String url = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode";
        String apiUrl = UriComponentsBuilder.newInstance()
                .fromHttpUrl(url)
                .queryParam("query", "{query}")
                .encode()
                .toUriString();

        String location = "서울 노원구 동일로 1493 상계주공아파트(10단지) 주공10단지종합상가111";

        Map<String, String> apiParams = new LinkedHashMap<>();
        apiParams.put("query", location);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-NCP-APIGW-API-KEY-ID", api_key);
        httpHeaders.add("X-NCP-APIGW-API-KEY", secret_key);
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        RestTemplate rt = new RestTemplate();

        ResponseEntity<String> geoRes = rt.exchange(apiUrl, HttpMethod.GET, httpEntity, String.class, apiParams);

        System.out.println(geoRes.getBody());
    }
}