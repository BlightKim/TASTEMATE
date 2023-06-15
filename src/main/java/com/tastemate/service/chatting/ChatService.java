package com.tastemate.service.chatting;

import com.tastemate.domain.chatting.ChatRoomVO;
import java.util.List;
import java.util.Map;
import org.springframework.web.socket.WebSocketSession;

public interface ChatService {
  public List<ChatRoomVO> getAllRoom();
  public ChatRoomVO getRoomById(String roomId);

  public ChatRoomVO createChatRoom(String roomName);
  public void deleteChatRoom(String roomId);
  public String getUserName(String roomId, String userUUID);

  public <T> void sendMessage(WebSocketSession session, T message);
}
