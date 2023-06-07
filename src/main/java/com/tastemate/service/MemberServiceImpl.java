package com.tastemate.service;
import com.tastemate.domain.ChatMessageVO;
import com.tastemate.domain.ChatRoomVO;
import com.tastemate.domain.MemberVO;
import com.tastemate.mapper.ChatDAO;
import com.tastemate.mapper.MemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("MemberService")
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDAO memberDAO;


    @Override
    public MemberVO login(MemberVO vo) {
        return memberDAO.login(vo);
    }
}