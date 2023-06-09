package com.tastemate.controller;

import com.tastemate.domain.MemberVO;
import com.tastemate.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MemberController {
    @Autowired
    MemberService memberService;
    @GetMapping("/login")
    public String testLogin(HttpServletRequest request,MemberVO vo){
        HttpSession session = request.getSession();
        System.out.println(vo.toString());
        MemberVO member = memberService.login(vo);
        session.setAttribute("user",member);
        return "chat/Index";
    }

}
