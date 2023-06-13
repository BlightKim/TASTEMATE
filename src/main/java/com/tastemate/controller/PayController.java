package com.tastemate.controller;


import com.tastemate.domain.*;
import com.tastemate.service.KakaoPay;
import com.tastemate.service.PaymentService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
@RequestMapping("/pay/*")
@Slf4j
public class PayController {

    @Setter(onMethod_ = @Autowired)
    private KakaoPay kakaopay;


   @Autowired
    private PaymentService paymentService;



    @GetMapping("/kakaoPay")
    public void kakaoPayGet() {
        log.info("kakaoPay get............................................");
    }

    //카카오페이 결제 요청 (ajax)
    @GetMapping ("/kakaoPayGo")
    @ResponseBody
    public KakaoPayReadyVO kakaoPay(int total_amount, String item_name,
                                    Model model, HttpServletRequest request
                                    , int userIdx, int storeIdx, int bookingIdx
                                    ) {
        log.info("kakaoPay post............................................");
        log.info("total_amount : " + total_amount);


        KakaoPayReadyVO readyResponse = kakaopay.kakaoPayReady(total_amount, item_name
                                        ,userIdx, storeIdx, bookingIdx);


        model.addAttribute("tid", readyResponse.getTid());
        log.info("tid : " + readyResponse.getTid());
        log.info("readyResponse : " + readyResponse);

        request.getSession().setAttribute("total_amount",total_amount);

        return readyResponse;
    }


    // 결제 승인 요청
    @GetMapping("/kakaoPaySuccess")
    public void kakaoPaySuccess(@RequestParam("pg_token") String pg_token,
                                HttpServletRequest request,
                                Model model) {
        log.info("kakaoPaySuccess get............................................");
        log.info("kakaoPaySuccess pg_token : " + pg_token);

        model.addAttribute("info", kakaopay.kakaoPayInfo(pg_token));


        //이거 DB에 저장? approval
    }


    // 카카오페이
    @PostMapping("/refund")
    public String refund(Model model) {

        log.info("controller refund............................................");

        KakaoCancelResponse kakaoCancelResponse = kakaopay.kakaoCancel();

        log.info("kakaoCancelResponse : " + kakaoCancelResponse);

        model.addAttribute("kakaoRefund", kakaoCancelResponse);

        return "redirect:/pay/kakaoPayRefund";
    }

    @GetMapping("/kakaoPayRefund")
    public void kakaoPayRefund(){

    }




    /*===================== 이니시스 ============================*/
    @PostMapping("/inicisComplete")
    @ResponseBody
    public int paymentComplete(@RequestBody InicisVO inicisVO, HttpServletResponse response) throws IOException, ParseException {

        log.info("inicisComplete!!!!!!!!!!!!!!! : " + inicisVO);

        String token = paymentService.getToken();
        log.info("token : " + token);


        //결제상태 iamport에 update
        String iamportUpdate = paymentService.iamportUpdate(inicisVO, token);
        log.info("iamportUpdate : " + iamportUpdate);




    //이후 DB 작업해서 insert되면 1되게 하자
/*        int res = paySV.insert_pay(pvo);
        if(res == 1) {
            Biz_memberVO bvo = memberSV.selectBizMember(pvo.getBiz_email());
            bvo.setPay_coupon(bvo.getPay_coupon()+5);
            System.out.println("paycoupon: " + bvo.getPay_coupon());
            res = paySV.updateBiz_pay(bvo);
            if(res == 1)
                System.out.println("biz_member pay coupon insert complete");
        }*/


        return 1;
    }

    @PostMapping("/inicisCancel")
    @ResponseBody
    public void inicisCancel(@RequestBody InicisRefundVO inicisRefundVO) throws IOException {

        log.info("inicisCancel!!!!!!!!!!!!!!! : " + inicisRefundVO);

        //토큰 DB에서 가져오기
        String token = "8be49d26c5a86b99d4bde74f3c9fbef08e7a30a9";

        paymentService.inicisRefund(inicisRefundVO, token);

        log.info("controller 환불 완료!!!");

    }


    @GetMapping("/inicisSuccess")
    public void inicisSuccess(){

    }




}
