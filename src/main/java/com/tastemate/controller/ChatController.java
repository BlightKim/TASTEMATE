package com.tastemate.controller;

import com.tastemate.domain.ChatMessageVO;
import com.tastemate.domain.ChatRoomVO;
import com.tastemate.domain.ChatUserVO;
import com.tastemate.domain.MemberVO;
import com.tastemate.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ChatController {
    @Autowired
    private ChatService chatService;

    @GetMapping("/chatListAdmin")//전체 채팅방 목록
    public String getChatListAdmin(Model model) {
        List<ChatRoomVO> list = chatService.getChatListAdmin();
        model.addAttribute("chat", list);
        return "chat/chatList";
    }

    @PostMapping("/getList")//개인 채팅방 목록
    public String getChatList(Model model, MemberVO vo) {
        List<ChatRoomVO> list = chatService.getChatList(vo);
        System.out.println("vo.toString() : " + vo.toString());
        model.addAttribute("room", list);
        return "chat/chatList";
    }

    @PostMapping("/getRoom")//채팅방 들어가기
    public String getRoom(Model model, ChatRoomVO vo) {
        ChatRoomVO room = chatService.getRoom(vo);
        List<MemberVO> userInfo = chatService.getChatUserInfo(vo);
        List<ChatMessageVO> message = chatService.getMessage(vo);

        model.addAttribute("room", room);
        model.addAttribute("info", userInfo);
        model.addAttribute("message", message);
        return "/chat/ChatRoom";
    }

    @PostMapping("/insertMessage")//메세지 보내기
    @ResponseBody
    public void insertMessage(ChatMessageVO vo) {
        chatService.insertMessage(vo);
    }


    @GetMapping("/roomExit")//채팅방 나가기
    @ResponseBody
    public void exitRoom(ChatUserVO vo) {
        chatService.exitRoom(vo);

    }

    @PostMapping("/getUserInfo")//채팅방 유저 정보 보기
    @ResponseBody
    public void getUserInfo(ChatRoomVO vo ,Model model){
        System.out.println("sideOpen() 실행");
        List<MemberVO>user = chatService.getChatUserInfo(vo);
        model.addAttribute("info",user);
    }


}
