package com.tastemate.controller;

import com.tastemate.domain.BookmarkVO;
import com.tastemate.domain.MemberVO;
import com.tastemate.domain.StoreVO;
import com.tastemate.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.util.List;

@Controller
@RequestMapping("/bookmark/*")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @GetMapping("/list")
    public String get(Model model) {
        List<BookmarkVO> bookmarkList = bookmarkService.bookmark_get();

        model.addAttribute("bookmarkList", bookmarkList);

        return "/bookmark/list";
    }

    @GetMapping("/get")
    public String getMap(Model model , @RequestParam int userIdx) {
        List<BookmarkVO> bookmarkList = bookmarkService.bookmarkList(userIdx);
        System.out.println("bookmarkList = " + bookmarkList);
        model.addAttribute("bookmarkList", bookmarkList);

        return "/bookmark/get";
    }

    @GetMapping("/delete")
    public String delete(HttpSession session, HttpServletRequest request, RedirectAttributes redirect, int bookmarkIdx) {
        //로그인 정보 받아오기
        MemberVO memberVO = (MemberVO) session.getAttribute("vo");
        System.out.println("memberVO = " + memberVO);
        int userIdx = memberVO.getUserIdx();
        
        
        //log.info("bno" + bno);
        int result = bookmarkService.bookmark_delete(bookmarkIdx);
        //log.info("delete result : " + result);
        
        redirect.addAttribute("userIdx", userIdx);
        return "redirect:/bookmark/get";
    }

    @GetMapping("/insert")
    public String insert (Model model, StoreVO storeVO, HttpSession session, HttpServletRequest request,
                          RedirectAttributes redirect,
                          @RequestParam int storeIdx, @RequestParam String storeName) {
        System.out.println("model = " + model);
        BookmarkVO bookmarkVO = new BookmarkVO();
        StoreVO storeVO1 = (StoreVO) model.getAttribute("storeVO");
        bookmarkVO.setStoreIdx(Integer.parseInt(storeVO1.getStoreIdx()));
        bookmarkVO.setStoreName(storeVO1.getStoreName());

        //로그인 정보 받아오기
        MemberVO memberVO = (MemberVO) session.getAttribute("vo");
        System.out.println("memberVO = " + memberVO);
        int userIdx = memberVO.getUserIdx();
        String userId = memberVO.getUserId();
        String userName = memberVO.getUserName();
        
        bookmarkVO.setUserIdx(userIdx);
        bookmarkVO.setUserId(userId);
        bookmarkVO.setUserName(userName);
        //log.info("bno" + bno);
        int result = bookmarkService.bookmark_insert(bookmarkVO);
        //log.info("delete result : " + result);

        redirect.addAttribute("userIdx", userIdx);

        return "redirect:/bookmark/get";
    }



}
