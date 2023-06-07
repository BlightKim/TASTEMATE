package com.tastemate.domain;

public class ChatRoomVO {
    private int roomIdx;
    private String roomName;
    private int roomStatus;

    public int getRoomIdx() {
        return roomIdx;
    }

    public void setRoomIdx(int roomIdx) {
        this.roomIdx = roomIdx;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(int roomStatus) {
        this.roomStatus = roomStatus;
    }

    @Override
    public String toString() {
        return "ChatRoomVO{" +
                "roomIdx=" + roomIdx +
                ", roomName='" + roomName + '\'' +
                ", roomStatus=" + roomStatus +
                '}';
    }
}

