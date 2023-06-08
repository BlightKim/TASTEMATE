
package com.tastemate.service;

import com.google.gson.JsonObject;
import com.tastemate.domain.InicisRefundVO;
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
import java.io.*;
import java.net.URL;






@Service
public class PaymentService {

    // IAMPORT API 인증 정보 설정
    String impKey = "3085212137161101";
    String impSecret = "hIvzsAXLBTySTTX2RPyr3KFfDWu4WBfvkGQb8mvCts3DBB4SsQ8pQ4uhEetSNdF5R0RaymFVBbrG2EbC";

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

    /*public String iamportUpdate(InicisVO inicisVO, String token) {

        String reqURL = "https://api.iamport.kr/payments/" + inicisVO.getImp_uid();
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // GET 요청
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", token);

            int responseCode = conn.getResponseCode();
            if(responseCode == 200) { // 결과 코드가 200이면 성공
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
        }catch(Exception e) {
            e.printStackTrace();
        }
        return "iamport update 실패";


    }*/
    
   
    
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



    public void processRefund(InicisRefundVO inicisRefundVO, String token) throws IOException {


            HttpsURLConnection conn = null;
            URL url = new URL("https://api.iamport.kr/payments/cancel");

            conn = (HttpsURLConnection) url.openConnection();

            conn.setRequestMethod("POST");

            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", token);

            conn.setDoOutput(true);

            JsonObject json = new JsonObject();

            json.addProperty("reason", inicisRefundVO.getReason());
            json.addProperty("merchant_uid", inicisRefundVO.getMerchant_uid());
            json.addProperty("amount", inicisRefundVO.getCancel_request_amount());


            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

            bw.write(json.toString());
            bw.flush();
            bw.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));


    }






}

