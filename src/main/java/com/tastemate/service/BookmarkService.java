package com.tastemate.service;

import com.tastemate.domain.BookmarkVO;
import com.tastemate.mapper.BookmarkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookmarkService {

    @Autowired
    private BookmarkMapper bookmarkMapper;

    public List<BookmarkVO> bookmark_get() {
        List<BookmarkVO> bookmarkList = bookmarkMapper.bookmark_getList();
        return bookmarkList;
    }
    public List<BookmarkVO> bookmarkList() {
        List<BookmarkVO> bookmarkList = bookmarkMapper.bookmarkList();
        return bookmarkList;
    }

}
