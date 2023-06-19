/*
package com.tastemate.controller;

import com.tastemate.domain.chatting.ChatRoomVO;
import com.tastemate.domain.chatting.ChatVO;
import com.tastemate.domain.chatting.ChatVO.MessageType;
import com.tastemate.service.chatting.ChatService;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatController {

  private final ChatService chatService;

  @GetMapping("/createRoom")
  public String createRoom(String roomName, Model model) {
    ChatRoomVO chatRoom = chatService.createChatRoom(roomName);
    model.addAttribute("chatRoom", chatRoom);
    return "chat/chat_view";
  }
}
*/
