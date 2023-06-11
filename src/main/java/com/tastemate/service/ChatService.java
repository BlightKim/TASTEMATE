package com.tastemate.service;

import com.tastemate.domain.ChatMessageVO;
import com.tastemate.domain.ChatRoomVO;
import com.tastemate.domain.ChatUserVO;
import com.tastemate.domain.MemberVO;
import com.tastemate.mapper.ChatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    private ChatMapper mapper;

    @Autowired
    public ChatService(ChatMapper mapper) {
        this.mapper = mapper;
    }

//    List<ChatRoomVO> getChatListAdmin();

    public List<ChatRoomVO> getChatList(MemberVO vo) {
        return mapper.getChatList(vo);
    }

    public List<ChatMessageVO> getMessage(ChatRoomVO vo) {
        return mapper.getMessage(vo);
    }

    public ChatRoomVO getRoom(ChatRoomVO vo) {
        return mapper.getRoom(vo);
    }

    public void insertMessage(ChatMessageVO vo) {
        mapper.insertMessage(vo);
    }

    public void exitRoom(ChatUserVO vo) {
        mapper.updateUserStatus(vo);
        int userCnt = mapper.getUserCount(vo);
        if (userCnt == 0) {
            mapper.updateRoomStatus(vo);
        }
    }

    ;

    public List<MemberVO> getChatUserInfo(ChatRoomVO vo) {
        return mapper.getChatUserInfo(vo);
    }

}