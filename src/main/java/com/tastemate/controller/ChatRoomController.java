package com.tastemate.controller;

import com.tastemate.WebSessionListener;
import com.tastemate.domain.MemberVO;
import com.tastemate.domain.chatting.ChatRoomVO;
import com.tastemate.domain.chatting.InviteDTO;
import com.tastemate.service.MemberService;
import com.tastemate.service.chatting.ChatService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {
  private final ChatService chatService;
  private final MemberService memberService;

  @ResponseBody
  @PostMapping("/createRoom")
  public Map<String, Object> createChatRoom(@RequestBody InviteDTO inviteDTO, Model model) {
    Map<String, Object> map = new HashMap<>();
    String roomName = inviteDTO.getRoomName();
    if(isExist(roomName)) {
      map.put("result", "alreadyExist");
    } else {
      ChatRoomVO newChatRoom = chatService.createChatRoom(roomName);
      map.put("result", "success");
      map.put("chatRoom", newChatRoom);
    }
    return map;
  }

  private boolean isExist(String roomName) {
    ChatRoomVO findRoom = chatService.getRoomByName(roomName);
    return findRoom == null ? false : true;
  }

  @ResponseBody
  @GetMapping("/room/{roomId}")
  public ChatRoomVO getChatRoom(String roomId) {
    ChatRoomVO chatRoom = chatService.getRoomById("1");
    return chatRoom;
  }
  @GetMapping("/chat/enter/{roomId}")
  public String findRoom(@PathVariable(name = "roomId") String roomId, Model model) {
    ChatRoomVO chatRoom = chatService.getRoomById(roomId);
    model.addAttribute("chatRoom", chatRoom);
    return "chat/chat_view";
  }
}
