package com.tastemate.controller;

import com.tastemate.domain.member.MemberVO;
import com.tastemate.form.MemberLoginForm;
import com.tastemate.service.login.LoginService;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

  private final LoginService loginService;

  @GetMapping
  public String loginForm(Model model) {
    MemberLoginForm loginForm = new MemberLoginForm();
    model.addAttribute("member", loginForm);
    return "member/login/loginform";
  }

  @PostMapping
  public String login(@ModelAttribute(name = "member") MemberLoginForm loginForm,
      HttpServletRequest request, @RequestParam(defaultValue = "/") String redirectURL) throws IOException {
    if (loginService.loginCheck(loginForm.getId(), loginForm.getPassword())) {
      MemberVO findMember = loginService.getMemberById(loginForm.getId());
      HttpSession session = request.getSession();
      session.setAttribute("userIdx", findMember.getUserIdx());
    }
    return "redirect:" + redirectURL;
  }

  @GetMapping("/logout")
  public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
    HttpSession session = request.getSession(false);
    session.invalidate();
    return "login_home";
  }
}
