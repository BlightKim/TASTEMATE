package com.tastemate.domain.chatting;

import com.tastemate.service.chatting.ChatService;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

@Data
public class ChatRoomVO {

  private String roomId;
  private String roomName;
  private Set<WebSocketSession> sessions = new HashSet<>();

  @Builder
  public ChatRoomVO(String roomId, String roomName) {
    this.roomId = roomId;
    this.roomName = roomName;
  }


  public void handleActions(WebSocketSession session, ChatVO chatVO, ChatService chatService) {
    if (chatVO.getType().equals(ChatVO.MessageType.ENTER)) {
      sessions.add(session);
      chatVO.setMessage(chatVO.getSender() + "님이 입장하셨습니다.");
    }
    sendMessage(chatVO, chatService);
  }

  private <T> void sendMessage(T message, ChatService chatService) {
    sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
  }
}
