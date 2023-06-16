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

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ChatController {
    private final  ChatService chatService;
    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/chat/getList")//개인 채팅방 목록
    @ResponseBody
    public List<ChatRoomVO> getChatList(MemberVO vo, HttpSession session) {
        vo = (MemberVO) session.getAttribute("vo");
        List<ChatRoomVO> room = chatService.getChatList(vo);
        System.out.println("room : " + room);
        return room;
    }


    @PostMapping("/chat/getRoom")//채팅방 들어가기
    public String getRoom(Model model, ChatRoomVO vo, HttpSession session) {
        // vo = (ChatRoomVO) session.getAttribute("vo");
        ChatRoomVO room = chatService.getRoom(vo);
        List<MemberVO> userInfo = chatService.getChatUserInfo(vo);
        List<ChatMessageVO> message = chatService.getMessage(vo);
        model.addAttribute("room", room);
        model.addAttribute("info", userInfo);
        model.addAttribute("message", message);
        return "/chat/ChatRoom";
    }

    @PostMapping("/chat/insertMessage")//메세지 보내기
    @ResponseBody
    public void insertMessage(ChatMessageVO vo) {
        chatService.insertMessage(vo);
    }


    @GetMapping("/chat/roomExit")//채팅방 나가기
    @ResponseBody
    public void exitRoom(ChatUserVO vo) {
        chatService.exitRoom(vo);
    }

    @ResponseBody
    @GetMapping("/chat/getChatUserInfo")//채팅방 유저 정보 보기
    public List<MemberVO> getChatUserInfo(ChatRoomVO vo) {
        //vo = (ChatRoomVO) session.getAttribute("vo");
        System.out.println("vo : >>>>>>>>>>" + vo);
        System.out.println("sideOpen() 실행");
        List<MemberVO> user = chatService.getChatUserInfo(vo);
        System.out.println("userinfo : " + user);
        return user;
    }

    @ResponseBody
    @PostMapping("/chat/matching")
    public ChatRoomVO matching(Model model, HttpSession session) {
        MemberVO vo1 = (MemberVO) session.getAttribute("vo");
        MemberVO vo2 = chatService.matchingUser();
        ChatRoomVO newRoomVO = new ChatRoomVO();
        newRoomVO.setRoomName(vo1.getUserName() + "," + vo2.getUserName() + " 대화방");
        System.out.println(">>roomName : " + newRoomVO);
        int roomIdx = chatService.createRoom(newRoomVO);
        System.out.println(">>roomIdx : " + roomIdx);
        ChatUserVO user1 = new ChatUserVO();
        user1.setRoomIdx(roomIdx);
        user1.setUserIdx(vo1.getUserIdx());
        chatService.insertChatRoomUser(user1);
        System.out.println("user1 : " + user1);



        ChatUserVO user2 = new ChatUserVO();
        user2.setRoomIdx(roomIdx);
        user2.setUserIdx(vo2.getUserIdx());
        chatService.insertChatRoomUser(user2);
        System.out.println("user2 : " + user2);
        newRoomVO.setRoomIdx(roomIdx);
        ChatRoomVO newRoom = chatService.getRoom(newRoomVO);


        System.out.println("newRoomVO : " + newRoomVO);
        System.out.println("newRoom : " + newRoom);
        return newRoom;
    }

}
