package com.tastemate.controller;

import com.tastemate.WebSessionListener;
import com.tastemate.domain.MemberVO;
import com.tastemate.domain.chatting.ChatRoomVO;
import com.tastemate.domain.chatting.InviteDTO;
import com.tastemate.domain.chatting.RoomRequest;
import com.tastemate.service.MemberService;
import com.tastemate.service.chatting.ChatService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
  @GetMapping("/createRoom")
  public ChatRoomVO createChatRoom() {
    String roomId = UUID.randomUUID().toString();
    Integer result = chatService.createChatRoom(roomId);
    if (result == 1) {
      ChatRoomVO chatRoom = chatService.getRoomById(roomId);
      log.info("chatRoom: {}", chatRoom);
      return chatRoom;
    }
    return null;
  }

/*  private boolean isExist(String roomId) {
    ChatRoomVO findRoom = chatService.getRoomById(roomId);
    return findRoom == null ? false : true;
  }*/

  @ResponseBody
  @PostMapping("/joinRoom")
  public Map<String, Object> map (@RequestBody RoomRequest roomRequest) {
    log.info("roomRequest: {}", roomRequest);
    Map<String, Object> map = new HashMap<>();
    map.put("senderId", roomRequest.getSenderId());
    map.put("inviteeId", roomRequest.getInviteeId());
    map.put("roomIdx", roomRequest.getRoomIdx());
    Integer result = chatService.joinRoom(map);
    log.info("result: {}", result);
    if(result ==2) {
      map.put("result", "success");
    } else {
      map.put("result", "fail");
    }
    return map;
  }
  @GetMapping("/room/{roomId}")
  public String enterRoom(@PathVariable(name = "roomId") String roomId,
      @RequestParam String myId,
      @RequestParam String yourId,
      Model model) {
    ChatRoomVO chatRoom = chatService.getRoomById(roomId);
    model.addAttribute("chatRoom", chatRoom);
    model.addAttribute("myId", myId);
    model.addAttribute("yourId", yourId);
    return "chat/chat_view";
  }

  @GetMapping("/showChatRoom")
  public String showChatRoom(Model model) {
    WebSessionListener webSessionListener = WebSessionListener.getInstance();
    List<MemberVO> currentUserList = webSessionListener.currentUserList();
    model.addAttribute("currentUserList", currentUserList);
    return "chat/tastemate_matching";
  }

  @ResponseBody
  @GetMapping("/deleteRoom/{roomId}")
  public String deleteChatRoom(@PathVariable(name = "roomId") String roomId) {
    Integer result = chatService.deleteChatRoom(roomId);
    log.info("result: {}", result);
    if(result == 1) {
      return "success";
    } else {
      return "fail";
    }
  }
}
