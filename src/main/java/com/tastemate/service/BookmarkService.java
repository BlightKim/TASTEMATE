package com.tastemate.service;

import com.tastemate.domain.BookmarkVO;
import com.tastemate.mapper.BookmarkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

@Service
public class BookmarkService {

    @Autowired
    private BookmarkMapper bookmarkMapper;

    public List<BookmarkVO> bookmark_get() {
        List<BookmarkVO> bookmarkList = bookmarkMapper.bookmark_getList();
        return bookmarkList;
    }
    public List<BookmarkVO> bookmarkList(int userIdx) {
        List<BookmarkVO> bookmarkList = bookmarkMapper.bookmarkList(userIdx);
        return bookmarkList;
    }
    public int bookmark_delete(int bookmarkIdx) {
        int result = bookmarkMapper.bookmark_delete(bookmarkIdx);
        return result;
    }


    public int bookmark_insert(BookmarkVO bookmarkVO) {
        int storeIdx = bookmarkVO.getStoreIdx();
        String userId = bookmarkVO.getUserId();
        System.out.println("userId = " + userId);
        System.out.println("storeIdx = " + storeIdx);
        System.out.println("bookmarkVO = " + bookmarkVO);
        BookmarkVO bookmarkVO1 = bookmarkMapper.bookmarkValidate(bookmarkVO);
        System.out.println("bookmarkVO1 = " + bookmarkVO1);
        int result = 0;
        if (bookmarkVO1 != null) {
            return result;
        } else {
            result = bookmarkMapper.bookmark_insert(bookmarkVO);
            return result;
        }

    }
}
