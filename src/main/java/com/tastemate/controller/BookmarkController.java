package com.tastemate.controller;

import com.tastemate.domain.BookmarkVO;
import com.tastemate.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String getMap(Model model) {
        List<BookmarkVO> bookmarkList = bookmarkService.bookmarkList();
        System.out.println("bookmarkList = " + bookmarkList);
        model.addAttribute("bookmarkList", bookmarkList);

        return "/bookmark/get";
    }

}
