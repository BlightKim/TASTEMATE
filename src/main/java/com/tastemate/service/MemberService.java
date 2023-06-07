package com.tastemate.service;
import com.tastemate.domain.ChatMessageVO;
import com.tastemate.domain.ChatRoomVO;
import com.tastemate.domain.MemberVO;

import java.util.List;

public interface MemberService {
    MemberVO login(MemberVO vo);

}
