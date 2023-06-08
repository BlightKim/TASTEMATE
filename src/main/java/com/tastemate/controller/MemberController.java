package com.tastemate.controller;

import com.tastemate.domain.MemberVO;
import com.tastemate.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/member/*")
@Slf4j
public class MemberController {

    private final MemberService service;

    @Autowired
    public MemberController(MemberService service) {
        this.service = service;
    }

    // 메인 페이지
    @GetMapping("member/list")
    public String get(Model model, MemberVO vo, HttpServletRequest request, HttpSession session) {
        vo = (MemberVO) session.getAttribute("vo");
        System.out.println("vo = " + vo);
        session.setAttribute("vo", vo);
//        List<memberVO> memberVO = service.user_get();

            return "member/list";
    }
    // login 홈페이지 들어가기
    @GetMapping("member/login")
    public String loginGet(Model model) {
        return "member/login/loginForm";
    }

    // login 홈페이지 POST
    @PostMapping("login")
    public String loginPost(Model model, HttpServletRequest request, HttpSession session) {
        String userId = request.getParameter("userId");
        String userPwd = request.getParameter("userPwd");
        System.out.println("userId = " + userId);
        System.out.println("userPwd = " + userPwd);
        MemberVO vo = service.loginId(userId);
        String[] addressSplit = vo.getUserAddress().split(",");

        if(vo.getUserStatus() == 0) {
            if (userPwd.equals(vo.getUserPwd())) {
                session.setAttribute("vo", vo);
                session.setAttribute("addressSplit", addressSplit);
                System.out.println("vo = " + vo);
                System.out.println(vo.getUserPwd());
                return "redirect:/member/list";
            } else {
                System.out.println("비밀번호가 다릅니다.");
                return "member/login/loginForm";
            }
        } else {
            System.out.println("휴면상태의 아이디입니다.");
            return "member/login/loginForm";
        }
    }

    @GetMapping("member/logout")
    public String logoutPost(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/member/list";
    }

    @PostMapping("findId")
    public String findId(MemberVO vo, HttpSession session, HttpServletRequest request) {

        return "redirect:/member/list";
    }

    // Food에서 href로 넘어오는 곳
    @GetMapping("member/register")
    public String registerMember(Model model, @RequestParam(name = "food") String food, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        food = food.replaceAll("\'", "");
        session.setAttribute("food", food);
        System.out.println("food = " + food);
        return "member/login/registerForm";
    }

    // 주소창에서 다이렉트로 회원가입페이지
    @GetMapping("member/simpleregister")
    public String simpleRegisterMember(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("userClass");
        session.removeAttribute("userMbti");
        session.removeAttribute("food");
        return "member/login/registerForm";
    }

    // 회원가입
    @PostMapping("/userJoin")
    public String registerMember(MemberVO vo, MultipartFile oriProfile) {
        log.info("userJoin도착");
        service.user_join(vo, oriProfile);
        return "redirect:/member/list";
    }


    @PostMapping("member/mbti")
    public String mbtiPost(Model model, @RequestParam(name = "userMbti") String userMbti, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.setAttribute("userMbti", userMbti);
        return "member/mbti";
    }

    @GetMapping("member/mbti")
    public String mbtiGet(Model model, @RequestParam(name = "userClass") Integer userClass, HttpServletRequest request) {

        HttpSession session = request.getSession();
        session.setAttribute("userClass", userClass);
        System.out.println("userClass = " + userClass);
        return "member/mbti";
    }

    @GetMapping("member/class")
    public String classesGet() {

        return "member/class";
    }

    @PostMapping("member/class")
    public String classesPost(Model model, @RequestParam(name = "userClass") Integer userClass, HttpServletRequest request) {
        return "member/mbti";
    }

    @GetMapping("member/selectFood")
    public String food(Model model, @RequestParam(name = "mbti") String userMbti, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        userMbti = userMbti.replaceAll("\'", "");
        session.setAttribute("userMbti", userMbti);
        System.out.println("userMbti = " + userMbti);

        return "member/selectFood";
    }

    // 회원가입 아이디 중복확인 ajax
    @PostMapping("member/checkId")
    @ResponseBody
    public Map<Object, Object> checkid(@RequestBody String userId) {
        System.out.println("REGISTER/CHECK_ID");
        System.out.println("userId = " + userId);
        Map<Object, Object> map = new HashMap<Object,Object>();
        Integer result = 0;

        result = service.checkId(userId);
        System.out.println("result = " + result);
        System.out.println("result. = " + result.getClass());
        map.put("check", result);

        return map;
    }

    @GetMapping("member/mypage")
    public String myPage(HttpServletRequest request, HttpSession session) {
        MemberVO vo = (MemberVO) session.getAttribute("vo");

        String[] addressSplit = vo.getUserAddress().split(",");
        System.out.println("addressSplit = " + addressSplit[0]);
        System.out.println("addressSplit = " + addressSplit[1]);
        System.out.println("addressSplit = " + addressSplit[2]);
        System.out.println("vo = " + vo);

        session.setAttribute("addressSplit", addressSplit);

        session.setAttribute("vo", vo);
        return "member/login/mypage";
    }

    @PostMapping("modify")
    public String userModify(MemberVO vo, MultipartFile oriProfile, HttpServletRequest request, HttpSession session) {
        MemberVO sessionVO = (MemberVO) session.getAttribute("vo");
        session.setAttribute("sessionVO", sessionVO);
        String[] addressSplit = (String[]) session.getAttribute("addressSplit");

        System.out.println(addressSplit[0]);
        System.out.println(addressSplit[1]);
        System.out.println(addressSplit[2]);

        session.setAttribute("addressSplit", addressSplit);

        log.info("modify도착");
        service.userModify(sessionVO, oriProfile, session, request);
        return "redirect:/member/list";
    }
    @PostMapping("status")
    public String userStatus(MemberVO vo, HttpServletRequest request, HttpSession session) {
        MemberVO sessionVO = (MemberVO) session.getAttribute("vo");
        session.setAttribute("sessionVO", sessionVO);

        System.out.println("sessionVO.getUserStatus() = " + sessionVO.getUserStatus());
        System.out.println("vo.getUserStatus() = " + vo.getUserStatus());

        log.info("status도착");
        service.userStatus(vo, session);
        return "redirect:/member/login";
    }
}

