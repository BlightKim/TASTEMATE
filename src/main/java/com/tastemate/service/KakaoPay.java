package com.tastemate.service;

import com.tastemate.domain.KakaoCancelResponse;
import com.tastemate.domain.KakaoPayApprovalVO;
import com.tastemate.domain.KakaoPayReadyVO;
import com.tastemate.mapper.PayMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Date;

@Service
@Slf4j
public class KakaoPay {

    @Autowired
    private PayMapper kakaoPayMapper;

    private static final String HOST = "https://kapi.kakao.com";

    private KakaoPayReadyVO kakaoPayReadyVO;
    private KakaoPayApprovalVO kakaoPayApprovalVO;



    //ajax 이용한 결제요청
    public KakaoPayReadyVO kakaoPayReady(int total_amount, String item_name
                                , int userIdx, int storeIdx, int bookingIdx) {

        log.info("kakaoPayReady..................");

        RestTemplate restTemplate = new RestTemplate();

        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "ed6d0aa9e5ff6298aac67594a7c69d07");
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");

        String orderNumber = generateOrderNumber();
        params.add("partner_order_id", orderNumber);
        params.add("partner_user_id", "TASTEMATE");
        params.add("item_name", item_name);
        params.add("quantity", "1");
        params.add("total_amount", String.valueOf(total_amount));
        params.add("tax_free_amount", "0");
        params.add("approval_url", "http://localhost:8080/pay/kakaoPaySuccess");
        params.add("cancel_url", "http://localhost:8080/pay/kakaoPayCancel");
        params.add("fail_url", "http://localhost:8080/pay/kakaoPaySuccessFail");

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);


        try {
            kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReadyVO.class);


            //DB insert
            kakaoPayReadyVO.setUserIdx(userIdx);
            kakaoPayReadyVO.setStoreIdx(storeIdx);
            kakaoPayReadyVO.setBookingIdx(bookingIdx);
            kakaoPayReadyVO.setPartnerOrderId(orderNumber);
            kakaoPayReadyVO.setTotalAmount(total_amount);

            log.info("kakaoPayReady : " + kakaoPayReadyVO);



            return kakaoPayReadyVO;

        } catch (RestClientException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return kakaoPayReadyVO;

    }



    //결제 승인
    public KakaoPayApprovalVO kakaoPayInfo(String pg_token) {

        log.info("KakaoPayInfoVO............................................");
        log.info("-----------------------------");

        RestTemplate restTemplate = new RestTemplate();

        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "ed6d0aa9e5ff6298aac67594a7c69d07");
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", kakaoPayReadyVO.getTid());
        params.add("partner_order_id", kakaoPayReadyVO.getPartnerOrderId());
        params.add("partner_user_id", "TASTEMATE");
        params.add("pg_token", pg_token);
        params.add("total_amount", String.valueOf(kakaoPayReadyVO.getTotalAmount()));


        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);


        // DB 연동 (kakaoPayReadyVO)

        kakaoPayReadyVO.setPgToken(pg_token);

        log.info("kakaoPayInfo : " + kakaoPayReadyVO);
        int result = kakaoPayMapper.kakaoPayReady_insert(kakaoPayReadyVO);
        log.info("kakaoPayReady_insert " + result);



        try {
            kakaoPayApprovalVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body, KakaoPayApprovalVO.class);


            // DB 연동 (kakaoPayApprovalVO)
            kakaoPayApprovalVO.setPgToken(pg_token);
            kakaoPayApprovalVO.setUserIdx(kakaoPayReadyVO.getUserIdx());
            kakaoPayApprovalVO.setStoreIdx(kakaoPayReadyVO.getStoreIdx());
            kakaoPayApprovalVO.setBookingIdx(kakaoPayReadyVO.getBookingIdx());
            kakaoPayApprovalVO.setTid(kakaoPayReadyVO.getTid());
            kakaoPayApprovalVO.setAmount2(kakaoPayReadyVO.getTotalAmount());
            log.info("" + kakaoPayApprovalVO);
            int result2 = kakaoPayMapper.kakaoPayApproval_insert(kakaoPayApprovalVO);
            log.info("kakaoPayApproval_insert " + result2);
            
            //추가로 결제되면 store 테이블에서 count +1되게

            return kakaoPayApprovalVO;

        } catch (RestClientException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }



    // 결제 환불
    public KakaoCancelResponse kakaoCancel(String tid) {

        log.info("service kakaoCancel............................................");

        // 카카오페이 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", "TC0ONETIME");
        //parameters.add("tid", "환불할 결제 고유 번호");
        parameters.add("tid", tid);

        int amount = kakaoPayMapper.get_amount(tid);
        parameters.add("cancel_amount", String.valueOf(amount));
        parameters.add("cancel_tax_free_amount", "0");
        parameters.add("cancel_vat_amount", "0");
       // parameters.add("cancel_available_amount", "4000");

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // DB 작업
        //kakaoPayApprovalVO 에서 status를 0이 결제완료인것
        // , 1이 결제 취소인것으로 변경할까??(delete가아니라)
        int result = kakaoPayMapper.delete_update(tid);
        log.info("delete_update : " + result);



        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        KakaoCancelResponse cancelResponse = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/cancel",
                requestEntity,
                KakaoCancelResponse.class);

        return cancelResponse;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        String auth = "KakaoAK " + "ed6d0aa9e5ff6298aac67594a7c69d07";

        httpHeaders.set("Authorization", auth);
        httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return httpHeaders;
    }


    /* 주문번호 생성 메서드 */
    public static String generateOrderNumber() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentTime = dateFormat.format(new Date());
        String randomNumber = generateRandomNumber(6); // 주문번호의 랜덤한 숫자 부분 길이 (여기서는 6자리로 설정)

        return currentTime + randomNumber;
    }

    public static String generateRandomNumber(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomNumber = random.nextInt(10);
            sb.append(randomNumber);
        }

        return sb.toString();
    }


    public KakaoPayApprovalVO getKakaoApproval(int userIdx) {
        log.info("getKakaoApproval 도착");

        return kakaoPayMapper.get_KakaoApproval(userIdx);
    }
}
