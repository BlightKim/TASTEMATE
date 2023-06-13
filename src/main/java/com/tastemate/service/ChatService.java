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

    /*    public HashSet<MemberVO> matchingUser(MemberVO vo) {

            HashSet<MemberVO> user = mapper.matchingUser(vo);
            HashSet<MemberVO> matchingUser = new HashSet<>();
            for (int i = 1; i <= 3; i++) {
                for (MemberVO matching : user) {
                    matchingUser.add(matching);
                    System.out.println(matching);
                }

            }
            return matchingUser;
        }*/
    public MemberVO matchingUser() {
        return mapper.matchingUser();
    }



    public void insertChatRoomUser(ChatUserVO vo) {
        mapper.insertChatRoomUser(vo);
    }
    public int createRoom(ChatRoomVO room){
          mapper.createRoom(room);
          return mapper.findLatestRoomIdx();
    };
}