
package com.tastemate.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tastemate.domain.InicisRefundVO;
import com.tastemate.domain.InicisVO;
import com.tastemate.domain.KakaoPayApprovalVO;
import com.tastemate.domain.TokenVO;
import com.tastemate.mapper.PayMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class PaymentService {


    @Autowired
    private PayMapper payMapper;

    // IAMPORT API 인증 정보 설정
    String impKey = "3085212137161101";
    String impSecret = "hIvzsAXLBTySTTX2RPyr3KFfDWu4WBfvkGQb8mvCts3DBB4SsQ8pQ4uhEetSNdF5R0RaymFVBbrG2EbC";

    public String getToken() throws ParseException {

        String tokenUrl = "https://api.iamport.kr/users/getToken";


        // 토큰 요청 데이터 설정
        MultiValueMap<String, String> requestData = new LinkedMultiValueMap<>();
        requestData.add("imp_key", impKey);
        requestData.add("imp_secret", impSecret);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(tokenUrl, requestData, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String accessToken = null;

            String tokenResponse = responseEntity.getBody();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // JSON 파싱
                JsonNode jsonNode = objectMapper.readTree(tokenResponse);

                // access_token 필드 추출
                accessToken = jsonNode.get("response").get("access_token").asText();

                // 추출한 access_token 사용
                log.info("access_token: " + accessToken);

            } catch (Exception e) {
                e.printStackTrace();
            }


            return accessToken;
        } else {
            return null;
        }
    }

    public String iamportUpdate(InicisVO inicisVO, String token) {

        String reqURL = "https://api.iamport.kr/payments/" + inicisVO.getImp_uid();
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // GET 요청
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", token);

            int responseCode = conn.getResponseCode();

            log.info("responseCode : " + responseCode);

            if (responseCode == 200) { // 결과 코드가 200이면 성공
                // 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = "";
                String result = "";

                while ((line = br.readLine()) != null) {
                    result += line;
                }

                // Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(result);

                JsonObject response = element.getAsJsonObject().get("response").getAsJsonObject();

                br.close();
                String paymentStatus = response.get("status").getAsString();
                return "iamport update 완료";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "iamport update 실패";


    }
    
   


    public void inicisRefund(InicisRefundVO inicisRefundVO) throws IOException {

        String cancelUrl = "https://api.iamport.kr/payments/cancel";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", inicisRefundVO.getToken());

        RestTemplate restTemplate = new RestTemplate();

        JSONObject requestData = new JSONObject();
        requestData.put("reason", inicisRefundVO.getReason());
        requestData.put("merchant_uid", inicisRefundVO.getMerchant_uid());
        requestData.put("imp_uid", inicisRefundVO.getImp_uid());
        requestData.put("amount", inicisRefundVO.getCancel_request_amount());

        log.info("확인 : "+requestData);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestData.toString(), headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(cancelUrl, HttpMethod.POST, requestEntity, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String responseData = responseEntity.getBody();
            log.info("responseData?"+responseData);

            // 처리 결과에 대한 작업 수행
        } else {
            // 요청 실패에 대한 작업 수행
        }

    }


    public int insert_inicis(InicisVO inicisVO) {

        return payMapper.insert_inicis(inicisVO);
    }

    public InicisVO get_inicis(int userIdx) {

        return payMapper.get_inicis(userIdx);
    }

    public int cancel_inicis(String merchant_uid) {

        return payMapper.cancel_inicis(merchant_uid);
    }

    public int updateStatus(int inicisIdx) {

        return payMapper.updateStatus(inicisIdx);
    }

    public int updateStatus2(int kakaoApprovalIdx) {

        return payMapper.updateStatus2(kakaoApprovalIdx);
    }

    public InicisVO findInicis(int userIdx) {

        return payMapper.findInicis(userIdx);
    }

    public KakaoPayApprovalVO findKakao(int userIdx) {

        return payMapper.findKakao(userIdx);
    }
}

