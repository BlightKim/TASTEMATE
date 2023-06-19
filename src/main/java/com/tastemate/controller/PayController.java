package com.tastemate.controller;


import com.tastemate.domain.*;
import com.tastemate.service.BookingService;
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

    @Autowired
    private BookingService bookingService;


    @GetMapping("/myPay")
    public String myPay(HttpSession session) {
        MemberVO memberVO = (MemberVO) session.getAttribute("vo");
        log.info("세션 memberVO 확인!" + memberVO);


        // 2개의 결제테이블에서 userIdx와 status가 0인것을 찾기
        // null값이 아니면 그거에따라 리턴값을 주소로 주자
        //둘다 null이면 payNothing으로 가자

        InicisVO inicisVO = paymentService.findInicis(memberVO.getUserIdx());
        KakaoPayApprovalVO kakaoPayApprovalVO = paymentService.findKakao(memberVO.getUserIdx());

        log.info("inicisVO 확인 : " + inicisVO);
        log.info("kakaoPayApprovalVO 확인 : " + kakaoPayApprovalVO);

        if (inicisVO != null) {
            log.info("redirect:/pay/inicisSuccess");
            return "redirect:/pay/inicisSuccess";
        } else if (kakaoPayApprovalVO != null) {
            log.info("redirect:/pay/mykakaoPaySuccess");
            return "redirect:/pay/mykakaoPaySuccess";
        }


        return "redirect:/pay/payNothing";
    }

    //카카오페이 결제 요청 (ajax)
    @GetMapping("/kakaoPayGo")
    @ResponseBody
    public KakaoPayReadyVO kakaoPay(int total_amount, String item_name,
                                    Model model, HttpServletRequest request
            , int userIdx, int storeIdx, int bookingIdx
    ) {
        log.info("kakaoPay post............................................");
        log.info("total_amount : " + total_amount);


        KakaoPayReadyVO readyResponse = kakaopay.kakaoPayReady(total_amount, item_name
                , userIdx, storeIdx, bookingIdx);


        model.addAttribute("tid", readyResponse.getTid());
        log.info("tid : " + readyResponse.getTid());
        log.info("readyResponse : " + readyResponse);

        request.getSession().setAttribute("total_amount", total_amount);

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
    public String mykakaoPaySuccess(HttpSession session, Model model) {
        log.info("mykakaoPaySuccess 도착!");

        MemberVO memberVO = (MemberVO) session.getAttribute("vo");
        log.info("세션 memberVO 확인!" + memberVO);

        try {
            KakaoPayApprovalVO info = kakaopay.getKakaoApproval(memberVO.getUserIdx());
            log.info("mykakaoPaySuccess info : " + info);

            if (info == null) {
                return "redirect:/pay/payNothing";
            }

            model.addAttribute("info", info);

        } catch (Exception e) {
            log.info("mykakaoPaySuccess 에러발생");
        }

        return "/pay/mykakaoPaySuccess";
    }

    @GetMapping("/payNothing")
    public void payNothing(@RequestParam(value = "message", required = false) String message,
                           RedirectAttributes rttr) {

        log.info("payNothing 도착");
        log.info("message : " + message);

        if (message != null) {
            rttr.addFlashAttribute("message", message);
            log.info("rttr");
        }

    }

    //카카오페이 취소 관련 시간 제한 aiax 처리 단
    @RequestMapping(value = "/timeCheck", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Integer> timeCheck(Model model, HttpSession session, HttpServletRequest request,
                                          @RequestParam("bookingIdx") int bookingIdx,
                                          @RequestParam("nowDate") String nowDate,
                                          @RequestParam("nowTime") String nowTime){
        Map<String, Integer> resultMap = new HashMap<>();
        BookingVO bookingVO = bookingService.bookingToPayShow(bookingIdx);
        System.out.println("bookingVO = " + bookingVO);
        String bookingTime = bookingVO.getBookingTime();
        System.out.println("bookingTime = " + bookingTime);
        String[] bookingT = bookingTime.split(" ");

        int timeResult = 0;
        for (int i = 0; i < bookingT.length; i++) {
            if (i == 0) {
                String[] str2 = bookingT[i].split("-");
                String[] str3 = nowDate.split("-");
                System.out.println("str2[0] = " + str2[0]);
                System.out.println("str3[0] = " + str3[0]);

                System.out.println("str2[1] = " + str2[1]);
                System.out.println("str3[1] = " + str3[1]);

                System.out.println("str2[2] = " + str2[2]);
                System.out.println("str3[2] = " + str3[2]);
                if (Integer.parseInt(str2[0]) == Integer.parseInt(str3[0]) &&
                        Integer.parseInt(str2[1]) == Integer.parseInt(str3[1]) &&
                        Integer.parseInt(str2[2]) == Integer.parseInt(str3[2]) ) {
                    timeResult++;
                } else if (Integer.parseInt(str2[0]) >= Integer.parseInt(str3[0]) &&
                        Integer.parseInt(str2[1]) >= Integer.parseInt(str3[1]) &&
                        Integer.parseInt(str2[2]) > Integer.parseInt(str3[2]) ) {
                    timeResult = 2;

                }
            }
            if (i == 1) {
                String[] str4 = bookingT[i].split(":");
                String[] str5 = nowTime.split(":");

                System.out.println("str4[0] = " + str4[0]);
                System.out.println("str5[0] = " + str5[0]);

                System.out.println("str4[1] = " + str4[1]);
                System.out.println("str5[1] = " + str5[1]);

                System.out.println("str4[2] = " + str4[2]);
                System.out.println("str5[2] = " + str5[2]);
                if ( Integer.parseInt(str4[0])-1 > Integer.parseInt(str5[0]) ||
                        (Integer.parseInt(str4[0])-1 == Integer.parseInt(str5[0]) && Integer.parseInt(str4[1]) >= Integer.parseInt(str5[1]) ) ) {
                    timeResult++;
                }
            }
        }
        resultMap.put("a", timeResult);
        resultMap.put("bookingIdx", bookingIdx);
        model.addAttribute("bookingIdx", bookingIdx);
        return  resultMap;
    }


    // 카카오페이
    @PostMapping("/refund")
    public String refund(Model model, String tid, int bookingIdx, RedirectAttributes rttr) {
        System.out.println("bookingIdx = " + bookingIdx);
        log.info("controller refund............................................");
        bookingService.bookingPayCancel(bookingIdx);


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
    public Map<String, String> inicisCancel(
            @RequestBody InicisRefundVO inicisRefundVO,
            RedirectAttributes rttr) throws IOException {

        int bookingIdx = inicisRefundVO.getBookingIdx();
        String nowDate = inicisRefundVO.getNowDate();
        String nowTime = inicisRefundVO.getNowTime();
        log.info("inicisCancel!!!!!!!!!!!!!!! : " + inicisRefundVO);
        BookingVO bookingVO = bookingService.bookingToPayShow(bookingIdx);
        System.out.println("bookingVO = " + bookingVO);
        String bookingTime = bookingVO.getBookingTime();
        System.out.println("bookingTime = " + bookingTime);
        System.out.println("nowDate = " + nowDate);
        System.out.println("nowTime = " + nowTime);

        String[] bookingT = bookingTime.split(" ");
        int timeResult = 0;
        for (int i = 0; i < bookingT.length; i++) {
            if (i == 0) {
                String[] str2 = bookingT[i].split("-");
                String[] str3 = nowDate.split("-");
                System.out.println("str2[0] = " + str2[0]);
                System.out.println("str3[0] = " + str3[0]);

                System.out.println("str2[1] = " + str2[1]);
                System.out.println("str3[1] = " + str3[1]);

                System.out.println("str2[2] = " + str2[2]);
                System.out.println("str3[2] = " + str3[2]);
                if (Integer.parseInt(str2[0]) == Integer.parseInt(str3[0]) &&
                        Integer.parseInt(str2[1]) == Integer.parseInt(str3[1]) &&
                        Integer.parseInt(str2[2]) == Integer.parseInt(str3[2])) {
                    timeResult++;
                } else if (Integer.parseInt(str2[0]) >= Integer.parseInt(str3[0]) &&
                        Integer.parseInt(str2[1]) >= Integer.parseInt(str3[1]) &&
                        Integer.parseInt(str2[2]) > Integer.parseInt(str3[2])) {
                    timeResult = 2;

                }
            }
            if (i == 1) {
                String[] str4 = bookingT[i].split(":");
                String[] str5 = nowTime.split(":");

                System.out.println("str4[0] = " + str4[0]);
                System.out.println("str5[0] = " + str5[0]);

                System.out.println("str4[1] = " + str4[1]);
                System.out.println("str5[1] = " + str5[1]);

                System.out.println("str4[2] = " + str4[2]);
                System.out.println("str5[2] = " + str5[2]);
                if (Integer.parseInt(str4[0]) - 1 > Integer.parseInt(str5[0]) ||
                        (Integer.parseInt(str4[0]) - 1 == Integer.parseInt(str5[0]) && Integer.parseInt(str4[1]) >= Integer.parseInt(str5[1]))) {
                    timeResult++;
                }
            }
        }

        System.out.println("timeResult = " + timeResult);

        Map<String, String> resultMap = new HashMap<>();
        if (timeResult >= 2) {
            //토큰 DB에서 가져오기
            String token = inicisRefundVO.getToken();

            paymentService.inicisRefund(inicisRefundVO);


            int result = paymentService.cancel_inicis(inicisRefundVO.getMerchant_uid());
            log.info("inicisCancel result : " + result);


            log.info("controller 환불 완료!!!");
            resultMap.put("a", "환불성공");

            String msg = "complete";
            rttr.addFlashAttribute("message", msg);
        } else {
            resultMap.put("a", "시간지남");
        }

        return resultMap;
    }


    @GetMapping("/inicisSuccess")
    public String inicisSuccess(HttpSession session, Model model) {

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

        } catch (Exception e) {
            log.info("inicisSuccess 에러발생");
        }

        return "/pay/inicisSuccess";
    }

    @GetMapping("/inicisRefund")
    public void inicisRefund() {

    }


}