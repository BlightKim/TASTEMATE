package com.tastemate.service;

import com.tastemate.domain.InicisVO;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

@Service
public class InicisService {

    // IAMPORT API 인증 정보 설정
    String impKey = "6605686173750148";
    String impSecret = "NKk4ASKxmOC9x0NvLUMy5qxOZ7QXCwGdfpGkoyR4aC2MwZdmfLjjksZJceYW3jJ0SmYY4c1ffnTm3M8W";

    public String getToken() {

        String tokenUrl = "https://api.iamport.kr/users/getToken";



        // 토큰 요청 데이터 설정
        MultiValueMap<String, String> requestData = new LinkedMultiValueMap<>();
        requestData.add("imp_key", impKey);
        requestData.add("imp_secret", impSecret);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(tokenUrl, requestData, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String tokenResponse = responseEntity.getBody();
            return tokenResponse;
        } else {
            return "못받음";
        }
    }

    public String iamportUpdate(InicisVO inicisVO, String token) throws IOException, ParseException {
        HttpsURLConnection conn = null;

        URL url = new URL("https://api.iamport.kr/payments/" + inicisVO.getImp_uid());

        conn = (HttpsURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", token);
        conn.setDoOutput(true);

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

        JSONParser parser = new JSONParser();

        JSONObject p = (JSONObject) parser.parse(br.readLine());

        String response = p.get("response").toString();

        p = (JSONObject) parser.parse(response);

        String amount = p.get("amount").toString();
        return "update  완료요";

    }

}
