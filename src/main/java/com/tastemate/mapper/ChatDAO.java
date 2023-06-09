package com.tastemate.mapper;

import com.tastemate.domain.ChatMessageVO;
import com.tastemate.domain.ChatRoomVO;
import com.tastemate.domain.ChatUserVO;
import com.tastemate.domain.MemberVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ChatDAO {
    @Autowired
    private SqlSessionTemplate mybatis;

    public List<ChatRoomVO> getChatListAdmin() {
        return mybatis.selectList("getChatListAdmin");
    }
    public List<ChatRoomVO> getChatList(MemberVO vo){
        return  mybatis.selectList("getList",vo);
    }

    public List<ChatMessageVO> getMessage(ChatRoomVO vo) {
        return mybatis.selectList("getMessage",vo);

    }
    public ChatRoomVO getRoom(ChatRoomVO vo) {
        return mybatis.selectOne("getRoom",vo);
    }

/*    public List<ChatMessageVO> getSysMessage(ChatRoomVO vo){
        return mybatis.selectList("getSysMessage",vo);
    };*/

    public void insertMessage(ChatMessageVO vo) {
        mybatis.insert("insertMessage",vo);
    }

    public void exitRoom(ChatUserVO vo) {
        mybatis.update("updateUserStatus", vo);
        int userCnt = mybatis.selectOne("getUserCount", vo);
        if (userCnt == 0) {
            mybatis.update("updateRoomStatus", vo);
        }
    }

    public List<MemberVO> getChatUserInfo(ChatRoomVO vo) {

        return mybatis.selectList("getChatUserInfo",vo);
    }


}
