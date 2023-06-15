package com.tastemate.service.chatting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tastemate.domain.chatting.ChatRoomVO;
import com.tastemate.mapper.ChatMapper;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{
  private final ChatMapper chatMapper;

  @Override
  public List<ChatRoomVO> getAllRoom() {
    return chatMapper.findAllRoom();
  }

  @Override
  public ChatRoomVO getRoomById(String roomId) {
    return chatMapper.findRoomById(roomId);
  }

  @Override
  public ChatRoomVO createChatRoom(String roomName) {
    String randomId = UUID.randomUUID().toString();
    ChatRoomVO chatRoomVO = ChatRoomVO.builder()
        .roomId(randomId)
        .roomName(roomName)
        .build();
    chatMapper.insertRoom(chatRoomVO);
    return chatRoomVO;
  }

  @Override
  public void deleteChatRoom(String roomId) {
    chatMapper.deleteRoom(roomId);

  }


  @Override
  public String getUserName(String roomId, String userUUID) {
    return null;
  }

  @Override
  public <T> void sendMessage(WebSocketSession session, T message) {

  }
}
