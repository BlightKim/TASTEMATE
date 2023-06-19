package com.tastemate.controller;

import com.tastemate.domain.BookmarkVO;
import com.tastemate.domain.MemberVO;
import com.tastemate.domain.StoreVO;
import com.tastemate.service.BookmarkService;
import com.tastemate.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bookmark/*")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private StoreService storeService;

    @GetMapping("/list")
    public String get(Model model) {
        List<BookmarkVO> bookmarkList = bookmarkService.bookmark_get();

        model.addAttribute("bookmarkList", bookmarkList);

        return "/bookmark/list";
    }

    @GetMapping("/get")
    public String getMap(Model model, @RequestParam int userIdx) {
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

    //ajax 북마크 추가
    @RequestMapping(value = "/insertAjax.do", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> insert(Model model, HttpSession session, HttpServletRequest request,
                                      RedirectAttributes redirect,
                                      @RequestParam("storeIdx") int storeIdx) {
        System.out.println("model = " + model);
        System.out.println("storeIdx : " + storeIdx);
        BookmarkVO bookmarkVO = new BookmarkVO();
        //StoreVO storeVO = storeService.store_get(Integer.parseInt(((StoreVO) model.getAttribute("storeVO")).getStoreIdx()));
        StoreVO storeVO = storeService.store_get(storeIdx);
        bookmarkVO.setStoreIdx(storeIdx);
        bookmarkVO.setStoreName(storeVO.getStoreName());

        //로그인 정보 받아오기
        MemberVO memberVO = (MemberVO) session.getAttribute("vo");
        System.out.println("memberVO = " + memberVO);
        Map<String, String> msg = new HashMap<String, String>();
        if (memberVO != null) {
            int userIdx = memberVO.getUserIdx();
            String userId = memberVO.getUserId();
            String userName = memberVO.getUserName();

            bookmarkVO.setUserIdx(userIdx);
            bookmarkVO.setUserId(userId);
            bookmarkVO.setUserName(userName);
            System.out.println("bookmarkVO : " + bookmarkVO);
            //log.info("bno" + bno);
            int result = bookmarkService.bookmark_insert(bookmarkVO);
            System.out.println("result = " + result);
            //log.info("delete result : " + result);
            if (result == 0) {
                msg.put("message", "이미 북마크에 담은 맛집입니다.");
                System.out.println(msg.toString());
                return msg;
            } else {
                msg.put("message", "북마크에 담겼습니다.");
                System.out.println(msg.toString());
                return msg;
            }

        } else {
            msg.put("message", "로그인 후 이용 가능한 기능입니다.");
            System.out.println(msg.toString());
            return msg;
        }


    }

    //ajax 북마크 삭제
    @RequestMapping(value = "/deleteAjax.do", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> deleteAjax(Model model, HttpSession session, HttpServletRequest request,
                                          RedirectAttributes redirect,
                                          @RequestParam("storeIdx") int storeIdx) {
        System.out.println("model = " + model);
        System.out.println("storeIdx : " + storeIdx);
        BookmarkVO bookmarkVO = new BookmarkVO();
        StoreVO storeVO = storeService.store_get(storeIdx);
        bookmarkVO.setStoreIdx(storeIdx);

        //로그인 정보 받아오기
        MemberVO memberVO = (MemberVO) session.getAttribute("vo");
        System.out.println("memberVO = " + memberVO);
        Map<String, String> msg = new HashMap<String, String>();
        if (memberVO != null) {
            int userIdx = memberVO.getUserIdx();

            bookmarkVO.setUserIdx(userIdx);
            System.out.println("bookmarkVO : " + bookmarkVO);
            //log.info("bno" + bno);
            int result = bookmarkService.bookmark_deleteAjax(bookmarkVO);
            System.out.println("result = " + result);
            //log.info("delete result : " + result);
            if (result == 0) {
                msg.put("message", "북마크에 없는 맛집입니다.");
                System.out.println(msg.toString());
                return msg;
            } else {
                msg.put("message", "북마크에서 삭제되었습니다.");
                System.out.println(msg.toString());
                return msg;
            }

        } else {
            msg.put("message", "로그인 후 이용 가능한 기능입니다.");
            System.out.println(msg.toString());
            return msg;
        }


    }

}