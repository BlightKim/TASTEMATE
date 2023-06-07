package com.tastemate.controller;

import com.tastemate.domain.KakaoCancelResponse;
import com.tastemate.domain.KakaoPayReadyVO;
import com.tastemate.service.KakaoPay;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/pay/*")
@Slf4j
public class PayController {

    @Setter(onMethod_ = @Autowired)
    private KakaoPay kakaopay;


    @GetMapping("/kakaoPay")
    public void kakaoPayGet() {
        log.info("kakaoPay get............................................");
    }

/*    @PostMapping("/kakaoPay")
    public String kakaoPay() {
        log.info("kakaoPay post............................................");

        return "redirect:" + kakaopay.kakaoPayReady();

    } */

    //카카오페이 결제 요청 (ajax)
    @GetMapping ("/kakaoPay123")
    @ResponseBody
    public KakaoPayReadyVO kakaoPay(int total_amount, String item_name, Model model, HttpServletRequest request) {
        log.info("kakaoPay post............................................");
        log.info("total_amount : " + total_amount);

        KakaoPayReadyVO readyResponse = kakaopay.kakaoPayReady(total_amount, item_name);

        model.addAttribute("tid", readyResponse.getTid());
        log.info("tid : " + readyResponse.getTid());
        log.info("readyResponse : " + readyResponse);

        request.getSession().setAttribute("total_amount",total_amount);


        return readyResponse;

    }

    // 결제 승인 요청
    @GetMapping("/kakaoPaySuccess")
    public void kakaoPaySuccess(@RequestParam("pg_token") String pg_token,
                                //@RequestParam("total_amount") int total_amount,
                                HttpServletRequest request,
                                Model model) {
        log.info("kakaoPaySuccess get............................................");
        log.info("kakaoPaySuccess pg_token : " + pg_token);


        model.addAttribute("info", kakaopay.kakaoPayInfo(pg_token));
    }


    /**
     * 환불
     */
    @PostMapping("/refund")
    public ResponseEntity refund() {

        log.info("controller refund............................................");

        KakaoCancelResponse kakaoCancelResponse = kakaopay.kakaoCancel();

        return new ResponseEntity<>(kakaoCancelResponse, HttpStatus.OK);
    }
}
