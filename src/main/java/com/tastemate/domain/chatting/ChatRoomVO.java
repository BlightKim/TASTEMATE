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

  private Integer roomIdx;
  private String roomId;
  private Date regDate;

  public ChatRoomVO(Integer roomIdx, String roomId, Date regDate) {
    this.roomIdx = roomIdx;
    this.roomId = roomId;
    this.regDate = regDate;
  }
}
