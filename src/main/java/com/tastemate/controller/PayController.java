package com.tastemate.controller;


import com.tastemate.domain.*;
import com.tastemate.service.KakaoPay;
import com.tastemate.service.PaymentService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


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
    public String kakaoPaySuccess(@RequestParam("pg_token") String pg_token,
                                HttpServletRequest request,
                                Model model) {
        log.info("kakaoPaySuccess get............................................");
        log.info("kakaoPaySuccess pg_token : " + pg_token);

        kakaopay.kakaoPayInfo(pg_token);
        //model.addAttribute("info", kakaopay.kakaoPayInfo(pg_token));

        return "redirect:/pay/mykakaoPaySuccess";
    }

    @GetMapping("/mykakaoPaySuccess")
    public String mykakaoPaySuccess(HttpSession session, Model model){
        log.info("mykakaoPaySuccess 도착!");

        MemberVO memberVO = (MemberVO) session.getAttribute("vo");
        log.info("세션 memberVO 확인!"+memberVO);

        try {
            KakaoPayApprovalVO info = kakaopay.getKakaoApproval(memberVO.getUserIdx());
            log.info("mykakaoPaySuccess info : " + info);

            if(info == null){
                return "redirect:/pay/payNothing";
            }

            model.addAttribute("info", info);

        } catch (Exception e){
            log.info("mykakaoPaySuccess 에러발생");
        }

        return "/pay/mykakaoPaySuccess";
    }

    @GetMapping("/payNothing")
    public void payNothing(@RequestParam(value="message", required = false) String message,
                           RedirectAttributes rttr){

        log.info("payNothing 도착");
        log.info("message : "+ message);

        if (message != null){
            rttr.addFlashAttribute("message", message);
            log.info("rttr");
        }

    }


    // 카카오페이
    @PostMapping("/refund")
    public String refund(Model model, String tid, RedirectAttributes rttr) {

        log.info("controller refund............................................");

        KakaoCancelResponse kakaoCancelResponse = kakaopay.kakaoCancel(tid);

        log.info("kakaoCancelResponse : " + kakaoCancelResponse);

        model.addAttribute("kakaoRefund", kakaoCancelResponse);


        String msg = "complete";
        rttr.addFlashAttribute("message", msg);

        return "redirect:/pay/payNothing";
    }




    /*===================== 이니시스 ============================*/
    @PostMapping("/inicisComplete")
    @ResponseBody
    public int paymentComplete(@RequestBody InicisVO inicisVO) throws IOException, ParseException {

        log.info("inicisComplete!!!!!!!!!!!!!!! : " + inicisVO);

        String token = paymentService.getToken();
        log.info("token : " + token);

        
        // DB 넣기
        inicisVO.setToken(token);
        int result = paymentService.insert_inicis(inicisVO);
        log.info("insert_inicis 결과 : " + result);


        //결제상태 iamport에 update
        String iamportUpdate = paymentService.iamportUpdate(inicisVO, token);
        log.info("iamportUpdate : " + iamportUpdate);

        return result;
    }

    @RequestMapping(value = "/inicisCancel", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> inicisCancel(@RequestBody InicisRefundVO inicisRefundVO
    ,RedirectAttributes rttr) throws IOException {

        log.info("inicisCancel!!!!!!!!!!!!!!! : " + inicisRefundVO);

        //토큰 DB에서 가져오기
        String token = inicisRefundVO.getToken();

        paymentService.inicisRefund(inicisRefundVO);

        int result = paymentService.cancel_inicis(inicisRefundVO.getMerchant_uid());
        log.info("inicisCancel result : " + result);

        log.info("controller 환불 완료!!!");
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("a", "환불성공");

        String msg = "complete";
        rttr.addFlashAttribute("message", msg);

        return resultMap;
    }


    @GetMapping("/inicisSuccess")
    public String inicisSuccess(HttpSession session, Model model){

        MemberVO memberVO = (MemberVO) session.getAttribute("vo");
        log.info("memberVO : " + memberVO);

        try {
            log.info("try catch 시작");
            InicisVO inicisVO = paymentService.get_inicis(memberVO.getUserIdx());
            log.info("inicisSuccess : " + inicisVO);

            if (inicisVO == null) {
                return "redirect:/pay/payNothing";
            }

            model.addAttribute("inicisVO", inicisVO);

        } catch (Exception e){
            log.info("inicisSuccess 에러발생");
        }

        return "/pay/inicisSuccess";
    }

    @GetMapping("/inicisRefund")
    public void inicisRefund(){

    }




}
