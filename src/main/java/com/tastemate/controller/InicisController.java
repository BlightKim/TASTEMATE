package com.tastemate.controller;

import com.tastemate.domain.BookingVO;
import com.tastemate.domain.InicisVO;
import com.tastemate.service.InicisService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/inicis/*")
@Slf4j
public class InicisController {

    @Autowired
    private InicisService inicisService;

    @GetMapping("/inicisPay")
    public String inicisPay(Model model, BookingVO bookingVO, @RequestBody InicisVO inicisVO)
            throws IOException, ParseException {
        return "pay/inicisPay";
    }

    @PostMapping("/inicisComplete")
    @ResponseBody
    public int paymentComplete(@RequestBody InicisVO inicisVO) throws IOException, ParseException {

        log.info("inicisComplete!!!!!!!!!!!!!!! : " + inicisVO);

        String token = inicisService.getToken();
        log.info("token : " + token);

        //결제상태 iamport에 update
        String iamportUpdate = inicisService.iamportUpdate(inicisVO, token);
        log.info("iamportUpdate : " + iamportUpdate);


        return 1;
    }


}
