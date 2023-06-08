package com.tastemate.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {
  @GetMapping("/")
  public String home(HttpServletRequest request) {
    HttpSession session = request.getSession(false);

    if(session == null) {
      return "login_home";
    } else {
      return "logout_home";
    }
  }
}
