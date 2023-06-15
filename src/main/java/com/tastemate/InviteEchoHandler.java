package com.tastemate;

import com.google.gson.Gson;
import com.tastemate.domain.MemberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class InviteEchoHandler extends TextWebSocketHandler {
    List<WebSocketSession> sessions = new ArrayList<>();
    Map<String, WebSocketSession> userSessions = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Map<String, Object> httpSession = session.getAttributes();
        String userId = (String) httpSession.get("userId");
        log.info("afterConnectionEstablished 실행, userId: {}", userId);
        userSessions.put(userId, session);
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("handleTextMessage 실행, message: {}, session: {}", message, session);
        Map<String, Object> attributes = session.getAttributes();
        String senderId = (String) attributes.get("userId");

        // protocol: 초대하는 사람, 초대 받는 사람, 메시지, URL
        String msg = message.getPayload();
        Gson gson = new Gson();  // Gson 객체 생성

        // 메시지의 내용을 문자열로 가져와 JSON 객체로 변환
        Map<String, String> msgMap = gson.fromJson(message.getPayload(), Map.class);
        String type = msgMap.get("type");
        if(type.equals("invite")) {
            invite(msgMap,senderId);
        }
    }

    private void invite(Map<String, String> msgMap, String senderId) {
        String sender = msgMap.get("sender");
        String inviteeId = msgMap.get("inviteeId");
        String url = msgMap.get("url");
        String toast = msgMap.get("toast");
        log.info("toast: {}", toast);
        WebSocketSession inviteeUser = null;

        if (userSessions.containsKey(inviteeId)) {
            TextMessage tmpMsg = new TextMessage(new Gson().toJson(msgMap));
            inviteeUser = userSessions.get(inviteeId);
            try {
                inviteeUser.sendMessage(tmpMsg);

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    }
}
