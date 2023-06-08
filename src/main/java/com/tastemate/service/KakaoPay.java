package com.tastemate.service;

import com.tastemate.domain.KakaoCancelResponse;
import com.tastemate.domain.KakaoPayApprovalVO;
import com.tastemate.domain.KakaoPayReadyVO;
import lombok.extern.slf4j.Slf4j;
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

@Service
@Slf4j
public class KakaoPay {

    private static final String HOST = "https://kapi.kakao.com";

    private KakaoPayReadyVO kakaoPayReadyVO;
    private KakaoPayApprovalVO kakaoPayApprovalVO;


    //결제 요청
   /* public String kakaoPayReady() {

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
        params.add("partner_order_id", "1234");
        params.add("partner_user_id", "bora");
        params.add("item_name", "강사님과 식사하기");
        params.add("quantity", "1");
        params.add("total_amount", "123456");
        params.add("tax_free_amount", "100");
        params.add("approval_url", "http://localhost:8080/pay/kakaoPaySuccess");
        params.add("cancel_url", "http://localhost:8080/pay/kakaoPayCancel");
        params.add("fail_url", "http://localhost:8080/pay/kakaoPaySuccessFail");

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        try {
            kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReadyVO.class);

            //log.info("" + kakaoPayReadyVO);

            return kakaoPayReadyVO.getNext_redirect_pc_url();

        } catch (RestClientException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return "/pay/kakaoPay";

    }*/


    //ajax 이용한 결제요청
    public KakaoPayReadyVO kakaoPayReady(int total_amount, String item_name) {

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
        params.add("partner_order_id", "1234");
        params.add("partner_user_id", "bora");
        params.add("item_name", item_name);
        params.add("quantity", "1");
        params.add("total_amount", String.valueOf(total_amount));
        params.add("tax_free_amount", "100");
        params.add("approval_url", "http://localhost:8080/pay/kakaoPaySuccess");
        params.add("cancel_url", "http://localhost:8080/pay/kakaoPayCancel");
        params.add("fail_url", "http://localhost:8080/pay/kakaoPaySuccessFail");

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        try {
            kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReadyVO.class);

            //log.info("" + kakaoPayReadyVO);

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
        params.add("partner_order_id", "1234");
        params.add("partner_user_id", "bora");
        params.add("pg_token", pg_token);
        params.add("total_amount", "54321");

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        try {
            kakaoPayApprovalVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body, KakaoPayApprovalVO.class);
            log.info("" + kakaoPayApprovalVO);

            return kakaoPayApprovalVO;

        } catch (RestClientException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }



    // 결제 환불
    public KakaoCancelResponse kakaoCancel() {

        log.info("service kakaoCancel............................................");

        // 카카오페이 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", "TC0ONETIME");
        //parameters.add("tid", "환불할 결제 고유 번호");
        parameters.add("tid", "T48174cf62ea0bae2003");   //DB에서 가져와야함!!!
        parameters.add("cancel_amount", "54321");
        parameters.add("cancel_tax_free_amount", "100");
        parameters.add("cancel_vat_amount", "0");
       // parameters.add("cancel_available_amount", "4000");

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

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

}
