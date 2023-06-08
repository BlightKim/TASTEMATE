package com.tastemate.service;

import com.tastemate.domain.BookmarkVO;
import com.tastemate.domain.MemberVO;
import com.tastemate.domain.StarVO;
import com.tastemate.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassService {

    @Autowired
    private MemberMapper memberMapper;


    public List<MemberVO> getClassList(int userClass) {
        List<MemberVO> classList = memberMapper.member_getClassList(userClass);
        return classList;
    }

    public List<MemberVO> user_getWithStar(int userClass) {
        List<MemberVO> userStar = memberMapper.user_getWithStar(userClass);
        return userStar;
    }
}
