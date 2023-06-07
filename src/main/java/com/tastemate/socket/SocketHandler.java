package com.tastemate.socket;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.HashMap;
import java.util.Map;

@Component
public class SocketHandler extends TextWebSocketHandler {


    private Map<String, Map<String, WebSocketSession>> chatRooms = new HashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // 메시지 발송
        String msg = message.getPayload();
        Map<String, WebSocketSession> sessionMap = getSessionMap(session);

        if (sessionMap != null) {
            for (WebSocketSession wss : sessionMap.values()) {
                try {
                    wss.sendMessage(new TextMessage(msg));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 소켓 연결
        super.afterConnectionEstablished(session);

        String roomIdx = getRoomIdx(session);
        if (roomIdx != null) {
            Map<String, WebSocketSession> sessionMap = chatRooms.computeIfAbsent(roomIdx, k -> new HashMap<>());
            sessionMap.put(session.getId(), session);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 소켓 종료
        String roomIdx = getRoomIdx(session);
        if (roomIdx != null) {
            Map<String, WebSocketSession> sessionMap = chatRooms.get(roomIdx);
            if (sessionMap != null) {
                sessionMap.remove(session.getId());
                if (sessionMap.isEmpty()) {
                    chatRooms.remove(roomIdx);
                }
            }
        }
        super.afterConnectionClosed(session, status);
    }

    private String getRoomIdx(WebSocketSession session) {
        String uri = session.getUri().toString();
        int idx = uri.lastIndexOf('/');
        if (idx != -1 && idx + 1 < uri.length()) {
            return uri.substring(idx + 1);
        }
        return null;
    }

    private Map<String, WebSocketSession> getSessionMap(WebSocketSession session) {
        String roomIdx = getRoomIdx(session);
        if (roomIdx != null) {
            return chatRooms.get(roomIdx);
        }
        return null;
    }
}

