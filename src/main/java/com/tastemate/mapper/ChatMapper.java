package com.tastemate.mapper;

import com.tastemate.domain.ChatMessageVO;
import com.tastemate.domain.ChatRoomVO;
import com.tastemate.domain.ChatUserVO;
import com.tastemate.domain.MemberVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {

    public List<ChatRoomVO> getChatList(MemberVO vo);

    public List<ChatMessageVO> getMessage(ChatRoomVO vo);

    public ChatRoomVO getRoom(ChatRoomVO vo);

    public void insertMessage(ChatMessageVO vo);

    public void updateUserStatus(ChatUserVO vo);

    public int getUserCount(ChatUserVO vo);

    public void updateRoomStatus(ChatUserVO vo);

    public List<MemberVO> getChatUserInfo(ChatRoomVO vo);


/*    public void exitRoom(ChatUserVO vo) {
        mybatis.update("updateUserStatus", vo);
        int userCnt = mybatis.selectOne("getUserCount", vo);
        if (userCnt == 0) {
            mybatis.update("updateRoomStatus", vo);
        }
    }*/


}
