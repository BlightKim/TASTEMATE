package com.tastemate.service;
import com.tastemate.domain.ChatMessageVO;
import com.tastemate.domain.ChatRoomVO;
import com.tastemate.domain.ChatUserVO;
import com.tastemate.domain.MemberVO;

import java.util.List;
public interface ChatService {
    List<ChatRoomVO> getChatListAdmin();
    List<ChatRoomVO> getChatList(MemberVO vo);
    List<ChatMessageVO> getMessage(ChatRoomVO vo);
    ChatRoomVO getRoom(ChatRoomVO vo);
    void insertMessage(ChatMessageVO vo);
    void exitRoom(ChatUserVO vo);
    List<MemberVO>getChatUserInfo(ChatRoomVO vo);
}
