package com.tastemate.handlers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tastemate.domain.MemberVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
@Slf4j
@Component
public class EchoHandler extends TextWebSocketHandler {

  private List<WebSocketSession> sessions = new ArrayList<>();
  private Map<String, WebSocketSession> users = new ConcurrentHashMap<>();

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    users.put(session.getId(), session);
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    String msg = message.getPayload();

    if (msg != null) {
      ObjectMapper mapper = new ObjectMapper();
      Map<String, String> data = (Map<String, String>) mapper.readValue(msg, new TypeReference<Map<String, String>>() {
      });
      String senderId = data.get("senderId");
      String inviteeId = data.get("inviteeId");
      String roomId = data.get("roomId");

      WebSocketSession targetSession = users.get(inviteeId);

      if (targetSession != null) {
        TextMessage tmpMsg = new TextMessage(
            new ObjectMapper().writeValueAsString(data));
        targetSession.sendMessage(tmpMsg);
      }
    }
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    sessions.remove(session);
  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    log.error(session.getId() + " 익셉션 발생: " + exception.getMessage());
  }
}
