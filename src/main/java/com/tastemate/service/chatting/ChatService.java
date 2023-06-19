package com.tastemate.service.chatting;

import com.tastemate.domain.chatting.ChatRoomVO;
import java.util.List;
import java.util.Map;
import org.springframework.web.socket.WebSocketSession;

public interface ChatService {
  public ChatRoomVO getRoomById(String roomId);

  public Integer createChatRoom(String roomId);
  public Integer deleteChatRoom(String roomId);

  Integer joinRoom(Map<String, Object> map);
}
