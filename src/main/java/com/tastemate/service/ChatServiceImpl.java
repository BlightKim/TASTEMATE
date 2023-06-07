package com.tastemate.service;
import com.tastemate.domain.ChatMessageVO;
import com.tastemate.domain.ChatRoomVO;
import com.tastemate.domain.ChatUserVO;
import com.tastemate.domain.MemberVO;
import com.tastemate.mapper.ChatDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service("chatService")
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatDAO chatDAO;
    @Override
    public List<ChatRoomVO> getChatListAdmin() {
        return chatDAO.getChatListAdmin();
    }

    @Override
    public List<ChatRoomVO> getChatList(MemberVO vo) {
        return chatDAO.getChatList(vo);
    }

    @Override
    public List<ChatMessageVO> getMessage(ChatRoomVO vo) {
        return chatDAO.getMessage(vo);
    }

/*    @Override
    public List<ChatMessageVO> getSysMessage(ChatRoomVO vo) {
        return chatDAO.getSysMessage(vo);
    }*/

    @Override
    public ChatRoomVO getRoom(ChatRoomVO vo) {
        return chatDAO.getRoom(vo);
    }

    @Override
    public void insertMessage(ChatMessageVO vo) {chatDAO.insertMessage(vo);}

    @Override
    public void exitRoom(ChatUserVO vo) {
        chatDAO.exitRoom(vo);
   }

    @Override
    public List<MemberVO> getChatUserInfo(ChatRoomVO vo) {
        return chatDAO.getChatUserInfo(vo);
    }


}