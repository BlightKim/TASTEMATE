package com.tastemate.mapper;

import com.tastemate.domain.chatting.ChatRoomVO;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatMapper {

  List<ChatRoomVO> findAllRoom();

  ChatRoomVO findRoomById(String roomId);

  void insertRoom(ChatRoomVO chatRoomVO);

  void deleteRoom(String roomId);

    ChatRoomVO findRoomByName(String roomName);
}
