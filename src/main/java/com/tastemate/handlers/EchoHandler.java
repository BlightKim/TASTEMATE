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
      ObjectMapper objectMapper = new ObjectMapper();
      Map<String, String> data = objectMapper.readValue(msg,
          new TypeReference<Map<String, String>>() {
          });

      String sender = data.get("sender");
      String receiver = data.get("receiver");
      String content = data.get("content");
      String url = data.get("url");

      WebSocketSession targetSession = users.get(receiver);

      if (targetSession != null) {
        TextMessage tmpMsg = new TextMessage(
            "<a target='_blank' href='" + url + "'>[<b>" + sender + "</b>] " + content + "</a>");
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
