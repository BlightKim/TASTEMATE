package com.tastemate.controller;

import com.tastemate.WebSessionListener;
import com.tastemate.domain.MemberVO;
import com.tastemate.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/member/*")
@Slf4j
public class MemberController {

    private final MemberService service;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberController(MemberService service, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
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
    public String loginGet(Model model, HttpSession session) {
        MemberVO vo = (MemberVO) session.getAttribute("sessionVo");
        session.setAttribute("sessionVo", vo);
        return "member/login/loginForm";
    }

    // login 홈페이지 POST
    @PostMapping("login")
    public String loginPost(Model model, HttpServletRequest request, HttpSession session, @RequestParam(name = "redirectURL", defaultValue = "/store/main") String redirectURL) {
        System.out.println("redirectURL = " + redirectURL);
        MemberVO sessionVo = (MemberVO) session.getAttribute("sessionVo");
        System.out.println("sessionVo = " + sessionVo);
        String userId = request.getParameter("userId");
        String userPwd = request.getParameter("userPwd");
        System.out.println("userId = " + userId);
        System.out.println("userPwd = " + userPwd);
        MemberVO vo = service.loginId(userId);

        String[] addressSplit = vo.getUserAddress().split(",");

            if (vo.getUserStatus() == 0) {
                if (passwordEncoder.matches(userPwd, vo.getUserPwd())) {
                    session.setAttribute("vo", vo);
                    session.setAttribute("addressSplit", addressSplit);
                  WebSessionListener webSessionListener = WebSessionListener.getInstance();
                  webSessionListener.sessionCreated(session);
                    System.out.println("vo = " + vo);
                    System.out.println(vo.getUserPwd());

                    if (vo.getUserType() == 0) {
                        return "/manage/main";
                    } else {
                        System.out.println("redirectURL = " + redirectURL);
                        return "redirect:" + redirectURL;
                    }
                } else {
                    System.out.println("비밀번호가 다릅니다.");
                    return "/member/login/loginForm";
                }
            } else {
                System.out.println("휴면상태의 아이디입니다.");
                return "/member/login/loginForm";
            }

    }

    @GetMapping("member/logout")
    public String logoutPost(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/store/main";
    }

    @GetMapping("member/findId")
    public String findId2(Model model, HttpServletRequest request, HttpSession session) {
        String userEmail = request.getParameter("userEmail");
        System.out.println("(getFindId)userEmail = " + userEmail);
        session.setAttribute("userEmail", userEmail);
        return "member/find";
    }

    @PostMapping("member/findId")
    public String findId(Model model, HttpServletRequest request, HttpSession session) {
        String userEmail = request.getParameter("userEmail");
        System.out.println("(postFindId)userEmail = " + userEmail);
        session.setAttribute("userEmail", userEmail);
        return "member/find";
    }

    @PostMapping("member/findIdByEmail")
    public String findIdByEmail(Model model, HttpServletRequest request, HttpSession session) {
        String userEmail = request.getParameter("userEmail");
        System.out.println("(findIdByEmail)userEmail = " + userEmail);
        MemberVO vo = service.findId(userEmail, request, session);
        System.out.println("vo = " + vo);
        if (vo == null) {
            return "redirect:/member/findId";
        }
        return "member/findIdByEmail";
    }

    @PostMapping("member/resetPassword")
    public String resetPassword(HttpServletRequest request, HttpSession session) {
        return "member/resetPassword";
    }

    @PostMapping("member/reset")
    public String reset(HttpServletRequest request, HttpSession session) {
//        request.getSession();
        String userId = request.getParameter("userId");
        String userEmail = request.getParameter("userEmail");
//        session.setAttribute("userId", userId);
//        session.setAttribute("userEmail", userEmail);

        System.out.println("id = " + userId);
        System.out.println("email = " + userEmail);

        MemberVO vo = service.userGet(userId, userEmail, request, session);
        session.setAttribute("userId", userId);

//        session.invalidate();

        System.out.println("(userGet)vo = " + vo);

        if (vo != null) {
            return "member/reset";
        }
        return "member/resetPassword";
    }

    @PostMapping("member/resetSuccess")
    public String resetSuccess(HttpServletRequest request, HttpSession session) {
        String userPwd = request.getParameter("userPwd");
//        String userId = (String) session.getAttribute("userId");
//        String userEmail = (String) session.getAttribute("userEmail");

//        System.out.println("(reset컨트롤러)userId = " + userId);
//        System.out.println("(reset컨트롤러)userEmail = " + userEmail);
        System.out.println("(컨트롤러)userPwd = " + userPwd);

        String userId = (String) session.getAttribute("userId");
        System.out.println("userId = " + userId);

        MemberVO vo = service.loginId(userId);

        service.reset(vo, request, session);
        session.invalidate();

        return "redirect:/store/main";
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
    public String registerMember(MemberVO vo, MultipartFile oriFilename) {
        log.info("userJoin도착");
        service.user_join(vo, oriFilename);
        return "redirect:/store/main";
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

    @GetMapping("tastemate")
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
    public Map<Object, Object> checkId(@RequestBody String userId) {
        System.out.println("REGISTER/CHECK_ID");
        System.out.println("userId = " + userId);
        Map<Object, Object> map = new HashMap<Object, Object>();
        Integer result = 0;

        result = service.checkId(userId);
        System.out.println("result = " + result);
        System.out.println("result.getClass() = " + result.getClass());
        map.put("check", result);

        return map;
    }

//    @PostMapping("member/check")
//    @ResponseBody
//    public Map<Object, Object> check(@RequestParam("id") String id, @RequestParam("pwd") String pwd, HttpServletRequest request,
//                                     HttpSession session) {
//        Map<Object, Object> map = new HashMap<Object, Object>();
//        Integer result = 0;
//        System.out.println("LOGIN_CHECK");
//        System.out.println("(check)userId = " + id);
//        System.out.println("(check)userPwd = " + pwd);
//
//        MemberVO selectedUser = service.loginId(id);
//        String[] addressSplit = selectedUser.getUserAddress().split(",");
//        System.out.println("selectedUser = " + selectedUser);
//
//        if (selectedUser.getUserStatus() == 0) {
//            if (selectedUser == null || !(passwordEncoder.matches(pwd, selectedUser.getUserPwd()))) {
//                session.setAttribute("vo", selectedUser);
//                session.setAttribute("addressSplit", addressSplit);
//                result = 1;
//                map.put("result", result);
//                return map;
//            } else {
//                result = 0;
//                map.put("result", result);
//            }
//            result = 0;
//            map.put("result", result);
//        }
//
//        return map;
//    }


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
        return "member/login/myPage";
    }

    @PostMapping("modify")
    public String userModify(MemberVO vo, MultipartFile oriFilename, HttpServletRequest request, HttpSession session,
                             RedirectAttributes rttr) {
        MemberVO sessionVO = (MemberVO) session.getAttribute("vo");
        session.setAttribute("sessionVO", sessionVO);
        String[] addressSplit = (String[]) session.getAttribute("addressSplit");

        System.out.println(addressSplit[0]);
        System.out.println(addressSplit[1]);
        System.out.println(addressSplit[2]);

        session.setAttribute("addressSplit", addressSplit);

        log.info("modify도착");
        service.userModify(sessionVO, oriFilename, session, request);

        String wow = "complete";
        rttr.addFlashAttribute("message", wow);

        return "redirect:/member/mypage";
    }

    @PostMapping("status")
    public String userStatus(MemberVO vo, HttpServletRequest request, HttpSession session) {
        MemberVO sessionVO = (MemberVO) session.getAttribute("vo");
        session.setAttribute("sessionVO", sessionVO);

        System.out.println("sessionVO.getUserStatus() = " + sessionVO.getUserStatus());
        System.out.println("vo.getUserStatus() = " + vo.getUserStatus());

        log.info("status도착");
        service.userStatus(vo, session);

        session.invalidate();
        return "redirect:/member/login";
    }

    @PostMapping("member/mailConfirm")
    @ResponseBody
    String mailConfirm(@RequestBody String userEmail) throws Exception {
        System.out.println("userEmail = " + userEmail);
        String[] split = userEmail.split("=");
        String convertEmail = split[1];
        System.out.println("split = " + convertEmail);

        String[] emailSplit = convertEmail.split("%");
        System.out.println("emailSplit = " + emailSplit[0]);

        String emailAddress = emailSplit[1];
        System.out.println("emailAddress = " + emailAddress);

        String address = emailAddress.substring(2, emailAddress.length());
        System.out.println("address = " + address);

        convertEmail.substring(11, convertEmail.length());
        String email = emailSplit[0] + "@" + address;

        System.out.println("email = " + email);

        String code = service.sendSimpleMessage(email);
        System.out.println("인증코드 : " + code);
        return code;
    }
}

