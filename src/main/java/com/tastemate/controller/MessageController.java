package com.tastemate.controller;

import com.tastemate.domain.chatting.ChatVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MessageController {

  private final SimpMessagingTemplate messagingTemplate;


  @MessageMapping(value = "/chat/enter")
  public void enter(ChatVO chatVO) {
    log.info("연결 성공");
    chatVO.setMessage(chatVO.getSender() + "님이 입장하셨습니다.");
    messagingTemplate.convertAndSend("/sub/chat/room/" + chatVO.getRoomId(), chatVO);
  }

  @MessageMapping(value = "/chat/message")
  public void message(ChatVO chatVO) {
    messagingTemplate.convertAndSend("/sub/chat/room/" + chatVO.getRoomId(), chatVO);
  }
}
