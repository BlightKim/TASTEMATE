package com.tastemate.mapper;

import com.tastemate.domain.BookmarkVO;
import org.apache.ibatis.annotations.Mapper;

import java.awt.print.Book;
import java.util.List;

@Mapper
public interface BookmarkMapper {

    public List<BookmarkVO> bookmark_getList();

    public List<BookmarkVO> bookmarkList(int userIdx);

    public int bookmark_delete(int bookmarkIdx);


    public int bookmark_insert(BookmarkVO bookmarkVO);

    public BookmarkVO bookmarkValidate(BookmarkVO bookmarkVO);
}
