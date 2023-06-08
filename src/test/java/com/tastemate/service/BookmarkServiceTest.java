package com.tastemate.service;

import com.tastemate.domain.BookmarkVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BookmarkServiceTest {
    @Autowired
    BookmarkService bookmarkService;

//    @Test
//    public void test() {
//        List<BookmarkVO> bookmarkVOList = bookmarkService.bookmarkList();
//
//        System.out.println("bookmarkVOList = " + bookmarkVOList);
//    }
}