package com.tastemate.mapper;
import com.tastemate.domain.ChatMessageVO;
import com.tastemate.domain.ChatRoomVO;
import com.tastemate.domain.MemberVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberDAO {
    @Autowired
    private SqlSessionTemplate mybatis;

    public List<ChatRoomVO> getChatListAdmin() {
        return mybatis.selectList("getChatListAdmin");
    }

    public MemberVO login(MemberVO vo) {
        return mybatis.selectOne( "login",vo);

        }
    }
