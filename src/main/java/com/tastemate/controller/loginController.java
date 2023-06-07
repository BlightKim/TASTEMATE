package com.tastemate.controller;

import com.tastemate.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class loginController {

    private final MemberService service;

    @Autowired
    public loginController(MemberService service) {
        this.service = service;
    }

    	// 로그인 폼으로 이동
        @GetMapping("/login/loginform")
        public String loginform() {
//		System.out.println("로그인폼");
            return "member/login/loginform";
        }

        // 일반 회원 회원가입 폼으로 이동
        @GetMapping("/member/registerForm")
        public String registerMember() {
            return "member/login/registerForm";
        }


}
