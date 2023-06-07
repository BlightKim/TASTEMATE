package com.tastemate.controller;

import com.tastemate.domain.member.UserStatus;
import com.tastemate.domain.member.MemberVO;
import com.tastemate.domain.member.ProfileVO;
import com.tastemate.domain.member.UploadFileStore;
import com.tastemate.form.MemberRegisterForm;
import com.tastemate.service.register.RegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/register")
@Slf4j
@RequiredArgsConstructor
public class RegisterController {

  private final RegisterService registerService;
  private final UploadFileStore profileStore;

  @GetMapping
  public String registerForm(Model model) {
    MemberRegisterForm memberRegisterForm = new MemberRegisterForm();
    model.addAttribute("member", memberRegisterForm);
    return "member/register/registerForm";
  }

  @PostMapping
  @ResponseBody
  String register(@ModelAttribute(name = "member") MemberRegisterForm registerForm) throws IOException {
    log.info("dir={}", profileStore.getFullPath("1234"));
    log.info("registerForm={}", registerForm);
    MemberVO member = formToMemberVO(registerForm);
    registerService.add(member);
    return "OK";
  }

  private MemberVO formToMemberVO(MemberRegisterForm registerForm) {
    MemberVO member = new MemberVO();
    member.setUserId(registerForm.getId());
    member.setUserPwd(registerForm.getPassword());
    member.setUserStatus(UserStatus.등록);
    return member;
  }
}
