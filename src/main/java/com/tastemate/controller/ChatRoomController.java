package com.tastemate.controller;

import com.tastemate.WebSessionListener;
import com.tastemate.domain.MemberVO;
import com.tastemate.domain.chatting.ChatRoomVO;
import com.tastemate.service.MemberService;
import com.tastemate.service.chatting.ChatService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ChatRoomController {
  private final ChatService chatService;
  private final MemberService memberService;

  @GetMapping("/chat/list")
  public String goChatRoom(Model model) {
    List<ChatRoomVO> chatRoomList = chatService.getAllRoom();
    List<MemberVO> memberList = memberService.user_get();
    model.addAttribute("memberList", memberList);
    return "chat/chat_list";
  }

  @GetMapping("/chat/createRoom")
  public String createChatRoom( String roomName,Model model) {
    ChatRoomVO chatRoom = chatService.createChatRoom(roomName);
    model.addAttribute("chatRoom", chatRoom);
    return "chat/chat_view";
  }
  @GetMapping("/chat/loginUserList")
  public String userList(Model model) {
    WebSessionListener webSessionListener = WebSessionListener.getInstance();
    List<MemberVO> currentUserList = webSessionListener.currentUserList();
    model.addAttribute("currentUserList", currentUserList);
    return "chat/login_user_list";
  }
  @GetMapping("/chat/test")
  public String test() {
    return "chat/chat_view";
  }
}
