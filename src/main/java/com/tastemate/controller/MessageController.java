package com.tastemate.controller;

import com.tastemate.domain.chatting.ChatVO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {
  private final SimpMessageSendingOperations sendingOperations;

  @MessageMapping("/chat/message")
  public void enter(ChatVO chatVO) {
    if(chatVO.getType().equals(ChatVO.MessageType.ENTER)) {
      chatVO.setMessage(chatVO.getSender() + "님이 입장하셨습니다.");
    } else if(chatVO.getType().equals(ChatVO.MessageType.LEAVE)) {
      chatVO.setMessage(chatVO.getSender() + "님이 퇴장하셨습니다.");
    }
    sendingOperations.convertAndSend("/queue/chat/room/" + chatVO.getRoomId(), chatVO);
  }
  @PostMapping("/chat/invite")
  public void invite() {

  }
}
