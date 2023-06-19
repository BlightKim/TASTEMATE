package com.tastemate.mapper;

import com.tastemate.domain.chatting.ChatRoomVO;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatMapper {


  ChatRoomVO findRoomById(String roomId);

  Integer insertRoom(String roomId);


  Integer joinRoom(Map<String, Object> map);

  Integer deleteRoom(String roomId);
}
