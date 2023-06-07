package com.tastemate.mapper;

import com.tastemate.domain.BookmarkVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookmarkMapper {

    public List<BookmarkVO> bookmark_getList();

    public List<BookmarkVO> bookmarkList();



}
