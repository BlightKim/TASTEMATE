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
  public ChatRoomVO getRoomById(String roomId) {
    return chatMapper.findRoomById(roomId);
  }

  @Override
  public Integer joinRoom(Map<String, Object> map) {
    return chatMapper.joinRoom(map);
  }

  @Override
  public Integer createChatRoom(String roomId) {
    return chatMapper.insertRoom(roomId);
  }

  @Override
  public Integer deleteChatRoom(String roomId) {
    return chatMapper.deleteRoom(roomId);
  }

   private Integer quitChatRoom(Integer roomIdx, String userId) {
    return null;
   }

   private Integer joinRoom(Integer roomIdx, String userId) {
    return null;
   }
  }
